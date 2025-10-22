package cn.iocoder.yudao.module.bpm.framework.flowable.core.listener;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.module.bpm.dal.dataobject.task.BpmTaskExtDO;
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
            log.warn("[taskCreated][任务{}找不到审批人，系统自动跳过。原因：{}]", 
                    task.getName(), reason);
            
            // 设置审批意见变量
            Map<String, Object> variables = new HashMap<>();
            variables.put("autoSkipped", true);
            variables.put("autoSkipReason", reason);
            variables.put("comment", "系统自动跳过：" + reason);
            
            // 清理临时变量
            variables.put("_approverNotFound_" + taskDefKey, null);
            variables.put("_autoSkipReason_" + taskDefKey, null);
            
            // 自动完成任务
            flowableTaskService.complete(task.getId(), variables);
            return;
        }
        
        // 情况2：检查是否审批人无效（需要自动通过）
        Object approverInvalid = flowableTaskService.getVariable(task.getId(), "_approverInvalid_" + taskDefKey);
        if (approverInvalid != null && Boolean.TRUE.equals(approverInvalid)) {
            String reason = (String) flowableTaskService.getVariable(task.getId(), "_autoApproveReason_" + taskDefKey);
            log.warn("[taskCreated][任务{}的审批人离职，自动通过任务。原因：{}]", 
                    task.getName(), reason);
            
            // 设置审批意见变量
            Map<String, Object> variables = new HashMap<>();
            variables.put("autoApproved", true);
            variables.put("autoApproveReason", reason);
            variables.put("comment", "系统自动通过：" + reason);
            
            // 清理临时变量
            variables.put("_approverInvalid_" + taskDefKey, null);
            variables.put("_autoApproveReason_" + taskDefKey, null);
            
            // 自动完成任务
            flowableTaskService.complete(task.getId(), variables);
        }
    }

    @Override
    protected void taskCompleted(FlowableEngineEntityEvent event) {
        taskService.updateTaskExtComplete((Task)event.getEntity());
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
