package cn.iocoder.yudao.module.bpm.framework.flowable.core.listener;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.iocoder.yudao.module.bpm.dal.dataobject.task.BpmTaskExtDO;
import cn.iocoder.yudao.module.bpm.dal.mysql.task.BpmTaskExtMapper;
import cn.iocoder.yudao.module.bpm.service.task.BpmActivityService;
import cn.iocoder.yudao.module.bpm.service.task.BpmTaskService;
import com.google.common.collect.ImmutableSet;
import lombok.extern.slf4j.Slf4j;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEntityEvent;
import org.flowable.common.engine.api.delegate.event.FlowableEngineEventType;
import org.flowable.engine.TaskService;
import org.flowable.engine.delegate.event.AbstractFlowableEngineEventListener;
import org.flowable.engine.delegate.event.FlowableActivityCancelledEvent;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.task.api.Task;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 监听 {@link org.flowable.task.api.Task} 的开始与完成，创建与更新对应的 {@link BpmTaskExtDO} 记录
 *
 * @author jason
 */
@Component
@Slf4j
public class BpmTaskEventListener extends AbstractFlowableEngineEventListener {

    @Resource
    @Lazy // 解决循环依赖
    private BpmTaskService taskService;

    @Resource
    @Lazy // 解决循环依赖
    private BpmActivityService activityService;

    @Resource
    @Lazy // 解决循环依赖
    private TaskService flowableTaskService;

    @Resource
    private BpmTaskExtMapper taskExtMapper;

    public static final Set<FlowableEngineEventType> TASK_EVENTS = ImmutableSet.<FlowableEngineEventType>builder()
            .add(FlowableEngineEventType.TASK_CREATED)
            .add(FlowableEngineEventType.TASK_ASSIGNED)
            .add(FlowableEngineEventType.TASK_COMPLETED)
            .add(FlowableEngineEventType.ACTIVITY_CANCELLED)
            .build();

    public BpmTaskEventListener(){
        super(TASK_EVENTS);
    }

    @Override
    protected void taskCreated(FlowableEngineEntityEvent event) {
        Task task = (Task) event.getEntity();
        
        // 创建任务扩展记录
        taskService.createTaskExt(task);
        
        String taskDefKey = task.getTaskDefinitionKey();
        
        // 情况1：检查是否找不到审批人（需要自动跳过）
        // 使用 flowableTaskService 查询流程变量（带 taskDefinitionKey）
        Object approverNotFound = flowableTaskService.getVariable(task.getId(), "_approverNotFound_" + taskDefKey);
        if (approverNotFound != null && Boolean.TRUE.equals(approverNotFound)) {
            String reason = (String) flowableTaskService.getVariable(task.getId(), "_autoSkipReason_" + taskDefKey);
            log.warn("[taskCreated][任务{}找不到审批人，将在事务提交后自动跳过。原因：{}]", 
                    task.getName(), reason);
            
            // 在事务提交后执行自动完成操作，避免事务冲突
            String taskId = task.getId();
            String taskName = task.getName();
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    try {
                        BpmTaskEventListener self = SpringUtil.getBean(BpmTaskEventListener.class);
                        self.autoCompleteTaskInNewTransaction(taskId, taskName, reason, true);
                    } catch (Exception e) {
                        log.error("[taskCreated][自动跳过任务{}失败]", taskName, e);
                    }
                }
            });
            return;
        }
        
        // 情况2：检查是否审批人无效（需要自动通过）
        Object approverInvalid = flowableTaskService.getVariable(task.getId(), "_approverInvalid_" + taskDefKey);
        if (approverInvalid != null && Boolean.TRUE.equals(approverInvalid)) {
            String reason = (String) flowableTaskService.getVariable(task.getId(), "_autoApproveReason_" + taskDefKey);
            log.warn("[taskCreated][任务{}的审批人离职，将在事务提交后自动通过。原因：{}]", 
                    task.getName(), reason);
            
            // 在事务提交后执行自动完成操作，避免事务冲突
            String taskId = task.getId();
            String taskName = task.getName();
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCommit() {
                    try {
                        BpmTaskEventListener self = SpringUtil.getBean(BpmTaskEventListener.class);
                        self.autoCompleteTaskInNewTransaction(taskId, taskName, reason, false);
                    } catch (Exception e) {
                        log.error("[taskCreated][自动通过任务{}失败]", taskName, e);
                    }
                }
            });
        }
    }
    
    /**
     * 在新事务中自动完成任务
     * 该方法会被事务提交后的回调调用，需要开启新事务
     * 
     * @param taskId 任务ID
     * @param taskName 任务名称
     * @param reason 自动完成原因
     * @param isSkip 是否为跳过（true为跳过，false为通过）
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW, rollbackFor = Exception.class)
    public void autoCompleteTaskInNewTransaction(String taskId, String taskName, String reason, boolean isSkip) {
        try {
            autoCompleteTask(taskId, taskName, reason, isSkip);
        } catch (Exception e) {
            log.error("[autoCompleteTaskInNewTransaction][任务{}处理失败]", taskName, e);
            throw e;
        }
    }
    
    /**
     * 自动完成任务
     * 优先尝试调用业务层的 JLBpmService.approveTask（如果存在）
     * 如果不存在，则直接调用 Flowable API 完成任务
     * 
     * @param taskId 任务ID
     * @param taskName 任务名称
     * @param reason 自动完成原因
     * @param isSkip 是否为跳过（true为跳过，false为通过）
     */
    private void autoCompleteTask(String taskId, String taskName, String reason, boolean isSkip) {
        try {
            // 获取任务信息
            Task task = flowableTaskService.createTaskQuery().taskId(taskId).singleResult();
            if (task == null) {
                log.error("[autoCompleteTask][任务{}不存在]", taskId);
                return;
            }
            
            // 获取流程实例
            org.flowable.engine.RuntimeService runtimeService = SpringUtil.getBean(org.flowable.engine.RuntimeService.class);
            org.flowable.engine.runtime.ProcessInstance instance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(task.getProcessInstanceId())
                    .singleResult();
            
            if (instance == null) {
                log.error("[autoCompleteTask][流程实例不存在]");
                completeTaskDirectly(taskId, taskName, reason, isSkip);
                return;
            }
            
            String processDefinitionKey = instance.getProcessDefinitionKey();
            Long refId = null;
            
            // 从 businessKey 获取 refId
            if (instance.getBusinessKey() != null) {
                try {
                    refId = Long.parseLong(instance.getBusinessKey());
                } catch (NumberFormatException e) {
                    log.warn("[autoCompleteTask][businessKey不是数字：{}]", instance.getBusinessKey());
                }
            }
            
            // 从 BPMN 定义中获取 taskStatus
            String taskStatus = null;
            try {
                org.flowable.engine.RepositoryService repositoryService = SpringUtil.getBean(org.flowable.engine.RepositoryService.class);
                org.flowable.bpmn.model.BpmnModel bpmnModel = repositoryService.getBpmnModel(task.getProcessDefinitionId());
                if (bpmnModel != null) {
                    org.flowable.bpmn.model.FlowElement flowElement = bpmnModel.getMainProcess().getFlowElement(task.getTaskDefinitionKey());
                    
                    if (flowElement instanceof org.flowable.bpmn.model.UserTask) {
                        org.flowable.bpmn.model.UserTask userTask = (org.flowable.bpmn.model.UserTask) flowElement;
                        
                        // 1. 先检查 ExtensionElements 中的 property 子元素
                        if (userTask.getExtensionElements() != null && !userTask.getExtensionElements().isEmpty()) {
                            for (java.util.Map.Entry<String, java.util.List<org.flowable.bpmn.model.ExtensionElement>> entry : 
                                    userTask.getExtensionElements().entrySet()) {
                                for (org.flowable.bpmn.model.ExtensionElement element : entry.getValue()) {
                                    // 检查子元素
                                    if (element.getChildElements() != null && !element.getChildElements().isEmpty()) {
                                        for (java.util.Map.Entry<String, java.util.List<org.flowable.bpmn.model.ExtensionElement>> childEntry : 
                                                element.getChildElements().entrySet()) {
                                            for (org.flowable.bpmn.model.ExtensionElement child : childEntry.getValue()) {
                                                // 检查子元素的属性（property元素的信息在属性中）
                                                if (child.getAttributes() != null && !child.getAttributes().isEmpty()) {
                                                    String propertyName = null;
                                                    String propertyValue = null;
                                                    
                                                    for (java.util.Map.Entry<String, java.util.List<org.flowable.bpmn.model.ExtensionAttribute>> childAttrEntry : 
                                                            child.getAttributes().entrySet()) {
                                                        for (org.flowable.bpmn.model.ExtensionAttribute childAttr : childAttrEntry.getValue()) {
                                                            if ("name".equals(childAttr.getName())) {
                                                                propertyName = childAttr.getValue();
                                                            } else if ("value".equals(childAttr.getName())) {
                                                                propertyValue = childAttr.getValue();
                                                            }
                                                        }
                                                    }
                                                    
                                                    // 如果找到 nextStatus 属性
                                                    if ("nextStatus".equals(propertyName) && propertyValue != null && taskStatus == null) {
                                                        taskStatus = propertyValue;
                                                        log.info("[autoCompleteTask][找到nextStatus：{}]", taskStatus);
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    
                                    // 检查元素本身的属性
                                    if (taskStatus == null && element.getAttributes() != null && !element.getAttributes().isEmpty()) {
                                        for (java.util.Map.Entry<String, java.util.List<org.flowable.bpmn.model.ExtensionAttribute>> attrEntry : 
                                                element.getAttributes().entrySet()) {
                                            for (org.flowable.bpmn.model.ExtensionAttribute attr : attrEntry.getValue()) {
                                                if ("nextStatus".equals(attr.getName()) && taskStatus == null) {
                                                    taskStatus = attr.getValue();
                                                    log.info("[autoCompleteTask][找到nextStatus：{}]", taskStatus);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        
                        // 2. 如果还没找到，检查 UserTask 的 Attributes
                        if (taskStatus == null && userTask.getAttributes() != null && !userTask.getAttributes().isEmpty()) {
                            for (java.util.Map.Entry<String, java.util.List<org.flowable.bpmn.model.ExtensionAttribute>> entry : 
                                    userTask.getAttributes().entrySet()) {
                                for (org.flowable.bpmn.model.ExtensionAttribute attr : entry.getValue()) {
                                    if ("nextStatus".equals(attr.getName())) {
                                        taskStatus = attr.getValue();
                                        log.info("[autoCompleteTask][找到nextStatus：{}]", taskStatus);
                                        break;
                                    }
                                }
                            }
                        }
                        
                        // 3. 如果还没找到，尝试通过命名空间查找
                        if (taskStatus == null) {
                            String[] namespaces = {
                                "http://flowable.org/bpmn",
                                "http://activiti.org/bpmn", 
                                "http://camunda.org/schema/1.0/bpmn",
                                "flowable",
                                "activiti"
                            };
                            
                            for (String namespace : namespaces) {
                                String value = userTask.getAttributeValue(namespace, "nextStatus");
                                if (value != null) {
                                    taskStatus = value;
                                    log.info("[autoCompleteTask][找到nextStatus：{}]", taskStatus);
                                    break;
                                }
                            }
                        }
                        
                        if (taskStatus == null) {
                            log.warn("[autoCompleteTask][未找到nextStatus属性，将使用默认值]");
                        }
                    }
                }
            } catch (Exception e) {
                log.error("[autoCompleteTask][获取taskStatus失败]", e);
            }
            
            // 如果没有获取到，使用默认值"3"（已批准）
            if (taskStatus == null) {
                taskStatus = "3";
                log.info("[autoCompleteTask][使用默认taskStatus：{}]", taskStatus);
            }
            
            // 尝试调用业务逻辑
            try {
                Object jlBpmService = SpringUtil.getBean("JLBpmServiceImpl");
                if (jlBpmService != null) {
                    jlBpmService.getClass()
                            .getMethod("executeApproveBusinessLogic", String.class, Long.class, String.class, String.class)
                            .invoke(jlBpmService, processDefinitionKey, refId, taskStatus, reason);
                }
            } catch (Exception e) {
                log.warn("[autoCompleteTask][调用业务逻辑失败，继续完成任务。错误：{}]", e.getMessage());
            }
            
            // 完成任务
            completeTaskDirectly(taskId, taskName, reason, isSkip);
            
        } catch (Exception e) {
            log.error("[autoCompleteTask][自动完成任务失败]", e);
            // 发生异常，尝试直接完成任务
            completeTaskDirectly(taskId, taskName, reason, isSkip);
        }
    }
    
    /**
     * 直接完成任务（不经过业务层）
     * 
     * @param taskId 任务ID
     * @param taskName 任务名称
     * @param reason 完成原因
     * @param isSkip 是否为跳过（true为跳过，false为通过）
     */
    private void completeTaskDirectly(String taskId, String taskName, String reason, boolean isSkip) {
        // 获取任务对象以便获取 taskDefinitionKey
        Task task = flowableTaskService.createTaskQuery().taskId(taskId).singleResult();
        if (task == null) {
            log.error("[completeTaskDirectly][任务{}不存在，无法完成]", taskId);
            return;
        }
        
        String taskDefKey = task.getTaskDefinitionKey();
        
        // 设置审批意见变量
        Map<String, Object> variables = new HashMap<>();
        if (isSkip) {
            variables.put("autoSkipped", true);
            variables.put("autoSkipReason", reason);
            variables.put("comment", "系统自动跳过：" + reason);
            // 清理临时变量
            variables.put("_approverNotFound_" + taskDefKey, null);
            variables.put("_autoSkipReason_" + taskDefKey, null);
        } else {
            variables.put("autoApproved", true);
            variables.put("autoApproveReason", reason);
            variables.put("comment", "系统自动通过：" + reason);
            // 清理临时变量
            variables.put("_approverInvalid_" + taskDefKey, null);
            variables.put("_autoApproveReason_" + taskDefKey, null);
        }
        
        // 自动完成任务
        flowableTaskService.complete(taskId, variables);
    }

    @Override
    protected void taskCompleted(FlowableEngineEntityEvent event) {
        Task task = (Task) event.getEntity();
        taskService.updateTaskExtComplete(task);
        
        // 检查是否是自动完成的任务，如果是，则更新 reason 字段
        Object autoSkipped = flowableTaskService.getVariable(task.getId(), "autoSkipped");
        Object autoApproved = flowableTaskService.getVariable(task.getId(), "autoApproved");
        
        String reason = null;
        if (autoSkipped != null && Boolean.TRUE.equals(autoSkipped)) {
            // 自动跳过
            String originalReason = (String) flowableTaskService.getVariable(task.getId(), "autoSkipReason");
            // 如果 originalReason 已经包含前缀，直接使用；否则添加前缀
            if (originalReason != null && originalReason.startsWith("系统自动跳过：")) {
                reason = originalReason;
            } else {
                reason = "系统自动跳过：" + originalReason;
            }
        } else if (autoApproved != null && Boolean.TRUE.equals(autoApproved)) {
            // 自动通过
            String originalReason = (String) flowableTaskService.getVariable(task.getId(), "autoApproveReason");
            // 如果 originalReason 已经包含前缀，直接使用；否则添加前缀
            if (originalReason != null && originalReason.startsWith("系统自动通过：")) {
                reason = originalReason;
            } else {
                reason = "系统自动通过：" + originalReason;
            }
        }
        
        // 如果有 reason，更新到扩展表
        if (reason != null) {
            taskExtMapper.updateByTaskId(
                new BpmTaskExtDO()
                    .setTaskId(task.getId())
                    .setReason(reason)
            );
        }
    }

    @Override
    protected void taskAssigned(FlowableEngineEntityEvent event) {
        taskService.updateTaskExtAssign((Task)event.getEntity());
    }

    @Override
    protected void activityCancelled(FlowableActivityCancelledEvent event) {
        List<HistoricActivityInstance> activityList = activityService.getHistoricActivityListByExecutionId(event.getExecutionId());
        if (CollUtil.isEmpty(activityList)) {
            log.error("[activityCancelled][使用 executionId({}) 查找不到对应的活动实例]", event.getExecutionId());
            return;
        }
        // 遍历处理
        activityList.forEach(activity -> {
            if (StrUtil.isEmpty(activity.getTaskId())) {
                return;
            }
            taskService.updateTaskExtCancel(activity.getTaskId());
        });
    }

}
