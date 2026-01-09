package cn.iocoder.yudao.module.jl.controller.admin.jlbpm.service;

import cn.iocoder.yudao.module.bpm.controller.admin.task.vo.instance.BpmProcessInstanceCancelReqVO;
import cn.iocoder.yudao.module.bpm.controller.admin.task.vo.task.BpmTaskApproveReqVO;
import cn.iocoder.yudao.module.bpm.controller.admin.task.vo.task.BpmTaskRejectReqVO;
import cn.iocoder.yudao.module.bpm.controller.admin.task.vo.task.BpmTaskReturnReqVO;
import cn.iocoder.yudao.module.bpm.framework.flowable.core.enums.BpmTaskStatustEnum;
import cn.iocoder.yudao.module.bpm.service.task.BpmProcessInstanceServiceImpl;
import cn.iocoder.yudao.module.bpm.service.task.BpmTaskServiceImpl;
import cn.iocoder.yudao.module.jl.controller.admin.jlbpm.vo.JLBpmTaskReqVO;
import cn.iocoder.yudao.module.jl.entity.crm.SalesleadOnly;
import cn.iocoder.yudao.module.jl.entity.project.ProjectApproval;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
import cn.iocoder.yudao.module.jl.entity.projectquotation.ProjectQuotation;
import cn.iocoder.yudao.module.jl.enums.*;
import cn.iocoder.yudao.module.jl.repository.commontask.CommonTaskRepository;
import cn.iocoder.yudao.module.jl.repository.crm.SalesleadOnlyRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProcurementItemRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProcurementRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectOnlyRepository;
import cn.iocoder.yudao.module.jl.repository.projectquotation.ProjectQuotationRepository;
import cn.iocoder.yudao.module.jl.repository.purchasecontract.PurchaseContractRepository;
import cn.iocoder.yudao.module.jl.service.commontask.CommonTaskServiceImpl;
import cn.iocoder.yudao.module.jl.service.crm.SalesleadServiceImpl;
import cn.iocoder.yudao.module.jl.service.project.ProjectApprovalServiceImpl;
import cn.iocoder.yudao.module.jl.service.project.ProjectServiceImpl;
import cn.iocoder.yudao.module.system.api.notify.NotifyMessageSendApi;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.FlowElement;
import org.flowable.bpmn.model.UserTask;
import org.flowable.engine.HistoryService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.web.core.util.WebFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.bpm.enums.ErrorCodeConstants.PROCESS_INSTANCE_NOT_EXISTS;
import static cn.iocoder.yudao.module.bpm.enums.ErrorCodeConstants.TASK_NOT_EXISTS;
import static cn.iocoder.yudao.module.bpm.service.utils.ProcessInstanceKeyConstants.*;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目的实验名目 Service 实现类
 */
@Service
@Validated
@Slf4j
public class JLBpmServiceImpl implements JLBpmService {

    @Resource
    private BpmTaskServiceImpl taskService;

    @Resource
    private BpmProcessInstanceServiceImpl processInstanceService;
    
    @Resource
    private RuntimeService runtimeService;
    
    @Resource
    private TaskService flowableTaskService;
    
    @Resource
    private RepositoryService repositoryService;

    @Resource
    private HistoryService historyService;

    @Resource
    private ProcurementRepository procurementRepository;

    @Resource
    private ProcurementItemRepository procurementItemRepository;

    @Resource
    private PurchaseContractRepository purchaseContractRepository;

    @Resource
    private ProjectOnlyRepository projectOnlyRepository;

    @Resource
    private ProjectServiceImpl projectService;

    @Resource
    private SalesleadOnlyRepository salesleadOnlyRepository;

    @Resource
    private SalesleadServiceImpl salesleadServiceImpl;

    @Resource
    private ProjectQuotationRepository projectQuotationRepository;

    @Resource
    private ProjectApprovalServiceImpl projectApprovalServiceImpl;

    @Resource
    private CommonTaskRepository commonTaskRepository;

    @Resource
    private CommonTaskServiceImpl commonTaskService;

    @Resource
    private NotifyMessageSendApi notifyMessageSendApi;

    @Override
    @Transactional
    public void approveTask(JLBpmTaskReqVO approveReqVO) {
        log.info("[approveTask][开始处理，任务ID：{}，reason：{}]", approveReqVO.getId(), approveReqVO.getReason());
        
        // 判断是否是系统自动审批（reason 包含"系统自动"）
        boolean isSystemAuto = approveReqVO.getReason() != null && 
                (approveReqVO.getReason().contains("系统自动跳过") || 
                 approveReqVO.getReason().contains("系统自动通过"));
        
        ProcessInstance instance;
        String processDefinitionKey;
        String taskDefinitionKey = null;
        
        if (isSystemAuto) {
            // 系统自动审批：绕过权限检查，直接通过 Flowable API 获取流程实例
            log.info("[approveTask][系统自动审批，绕过权限检查]");
            Task task = flowableTaskService.createTaskQuery()
                    .taskId(approveReqVO.getId())
                    .singleResult();
            if (task == null) {
                throw exception(TASK_NOT_EXISTS);
            }
            taskDefinitionKey = task.getTaskDefinitionKey();
            instance = runtimeService.createProcessInstanceQuery()
                    .processInstanceId(task.getProcessInstanceId())
                    .singleResult();
            if (instance == null) {
                throw exception(PROCESS_INSTANCE_NOT_EXISTS);
            }
            
            // 如果 taskStatus 为空，从 BPMN 定义中获取
            if (approveReqVO.getTaskStatus() == null) {
                String nextStatus = getTaskNextStatusFromBpmn(task.getProcessDefinitionId(), taskDefinitionKey);
                if (nextStatus != null) {
                    approveReqVO.setTaskStatus(nextStatus);
                    log.info("[approveTask][从BPMN获取taskStatus：{}]", nextStatus);
                } else {
                    // 如果 BPMN 中也没有定义，使用默认值"已批准"
                    approveReqVO.setTaskStatus(ProcurementStatusEnums.APPROVE.getStatus());
                    log.info("[approveTask][使用默认taskStatus（已批准）：{}]", approveReqVO.getTaskStatus());
                }
            }
        } else {
            // 正常审批：通过 BpmTaskService，会进行权限检查
            log.info("[approveTask][正常审批，进行权限检查]");
            instance = taskService.getProcessInstanceByTaskId(approveReqVO.getId());
        }

        processDefinitionKey = instance.getProcessDefinitionKey();
        
        // 如果 refId 为 null，尝试从流程变量中获取
        if (approveReqVO.getRefId() == null) {
            Object businessKey = instance.getBusinessKey();
            if (businessKey != null) {
                try {
                    approveReqVO.setRefId(Long.parseLong(businessKey.toString()));
                    log.info("[approveTask][从businessKey获取refId：{}]", approveReqVO.getRefId());
                } catch (NumberFormatException e) {
                    // businessKey 不是数字，忽略
                }
            }
        }
        
        // 执行业务逻辑
        executeApproveBusinessLogic(processDefinitionKey, approveReqVO.getRefId(), 
                approveReqVO.getTaskStatus(), approveReqVO.getReason());

        // 完成任务
        if (isSystemAuto) {
            // 系统自动审批：直接调用 Flowable API 完成任务，绕过权限检查
            log.info("[approveTask][系统自动审批，直接完成任务]");
            Map<String, Object> variables = new HashMap<>();
            variables.put("comment", approveReqVO.getReason());
            if (approveReqVO.getReason().contains("系统自动跳过")) {
                variables.put("autoSkipped", true);
                variables.put("autoSkipReason", approveReqVO.getReason());
            } else {
                variables.put("autoApproved", true);
                variables.put("autoApproveReason", approveReqVO.getReason());
            }
            flowableTaskService.complete(approveReqVO.getId(), variables);
            log.info("[approveTask][系统自动审批完成]");
        } else {
            // 正常审批：通过 BpmTaskService，会进行权限检查
            log.info("[approveTask][正常审批，调用BpmTaskService]");
            BpmTaskApproveReqVO bpmTaskApproveReqVO = new BpmTaskApproveReqVO();
            bpmTaskApproveReqVO.setId(approveReqVO.getId());
            bpmTaskApproveReqVO.setReason(approveReqVO.getReason());
            taskService.approveTask(getLoginUserId(), bpmTaskApproveReqVO);
            log.info("[approveTask][正常审批完成]");
        }
    }
    
    /**
     * 执行审批业务逻辑
     * 提取为独立方法，供正常审批和自动审批共同使用
     * 
     * @param processDefinitionKey 流程定义Key
     * @param refId 业务ID
     * @param taskStatus 任务状态
     * @param reason 审批意见
     */
    @Transactional
    public void executeApproveBusinessLogic(String processDefinitionKey, Long refId, 
                                            String taskStatus, String reason) {
        log.info("[executeApproveBusinessLogic][开始执行，processDefinitionKey：{}，refId：{}，taskStatus：{}]", 
                processDefinitionKey, refId, taskStatus);
        
        if (refId == null || taskStatus == null) {
            log.warn("[executeApproveBusinessLogic][跳过执行，refId或taskStatus为null]");
            return;
        }
        
        // 采购
        if (Objects.equals(processDefinitionKey, PROJECT_PROCUREMENT_AUDIT) || 
            Objects.equals(processDefinitionKey, OFFICE_PROCUREMENT_AUDIT) || 
            Objects.equals(processDefinitionKey, LAB_PROCUREMENT_AUDIT)) {
            log.info("[executeApproveBusinessLogic][执行采购审批业务]");
            
            procurementRepository.updateStatusById(refId, taskStatus);
            log.info("[executeApproveBusinessLogic][采购状态已更新为：{}]", taskStatus);
            
            if (Objects.equals(taskStatus, ProcurementStatusEnums.APPROVE.getStatus())) {
                procurementItemRepository.updateStatusByProcurementId(
                        ProcurementItemStatusEnums.APPROVE_PROCUREMENT.getStatus(), refId);
                procurementRepository.updateAcceptTimeById(LocalDateTime.now(), refId);
                procurementItemRepository.updatePurchaseAcceptTimeByProcurementId(LocalDateTime.now(), refId);
                log.info("[executeApproveBusinessLogic][采购已批准，同意时间已更新]");
            }
        }

        // 购销合同
        if (Objects.equals(processDefinitionKey, PROCUREMENT_PURCHASE_CONTRACT_AUDIT)) {
            log.info("[executeApproveBusinessLogic][执行购销合同审批业务]");
            
            purchaseContractRepository.updateStatusById(taskStatus, refId);
            log.info("[executeApproveBusinessLogic][购销合同状态已更新为：{}]", taskStatus);
            
            if (Objects.equals(taskStatus, PurchaseContractStatusEnums.APPROVE.getStatus())) {
                procurementItemRepository.updateStatusByPurchaseContractId(
                        ProcurementItemStatusEnums.ORDERED.getStatus(), refId);
                purchaseContractRepository.updateAcceptTimeById(LocalDateTime.now(), refId);
                procurementItemRepository.updateContractAcceptTimeByPurchaseContractId(LocalDateTime.now(), refId);
                log.info("[executeApproveBusinessLogic][购销合同已批准，同意时间已更新]");
            }
        }

        // 商机报价审核
        if (Objects.equals(processDefinitionKey, QUOTATION_AUDIT)) {
            log.info("[executeApproveBusinessLogic][执行商机报价审核业务]");
            processQuotationStatus(taskStatus, reason, refId);
        }

        // 项目状态变更
        if (Objects.equals(processDefinitionKey, PROJECT_STATUS_CHANGE)) {
            log.info("[executeApproveBusinessLogic][执行项目状态变更业务]");
            JLBpmTaskReqVO reqVO = new JLBpmTaskReqVO();
            reqVO.setRefId(refId);
            reqVO.setTaskStatus(taskStatus);
            reqVO.setReason(reason);
            processProjectStatusChangeBpm(reqVO);
        }

        // 项目出库
        if (Objects.equals(processDefinitionKey, PROJECT_OUTBOUND_APPLY)) {
            log.info("[executeApproveBusinessLogic][执行项目出库业务]");
            projectOnlyRepository.updateOutboundTimeById(LocalDateTime.now(), refId);
        }
        
        log.info("[executeApproveBusinessLogic][业务逻辑执行完成]");
    }
    @Transactional
    public void processProjectStatusChangeBpm(JLBpmTaskReqVO approveReqVO) {
        ProjectApproval projectApproval = projectApprovalServiceImpl.validateProjectApprovalExists(approveReqVO.getRefId());
        // 如果是开展前审批
        if(Objects.equals(projectApproval.getStage(), ProjectStageEnums.DOING_PREVIEW.getStatus())){

            ProjectSimple project = projectService.validateProjectExists(projectApproval.getProjectId());

            // 如果客户签字了
            if(project.getCustomerSignImgUrl()!=null&&project.getCustomerSignImgUrl().contains("http")){
                //直接改一下项目状态
                projectOnlyRepository.updateStageById(ProjectStageEnums.DOING.getStatus(),projectApproval.getProjectId());
                //改一下实验任务的状态，把 未下发的改为 开展中
                if(project.getCurrentQuotationId()!=null){
                    commonTaskService.sendTaskAndMsg(project.getCurrentQuotationId(), project.getName(), project.getId());
                }
            }
        }

        // 这个是项目变更状态的记录表，无论审批的是哪个状态，需要把这个记录同步更新一下
        projectApprovalServiceImpl.updateProjectApprovalByResultAndId(BpmTaskStatustEnum.APPROVE.getStatus().toString(), approveReqVO.getReason(), approveReqVO.getRefId());
    }



    @Transactional
    public void processQuotationStatus(String taskStatus,String reason, Long quotationId) {
        Optional<ProjectQuotation> byId = projectQuotationRepository.findById(quotationId);
        if(byId.isPresent()){
            if(byId.get().getSalesleadId()!=null){
                Optional<SalesleadOnly> byId1 = salesleadOnlyRepository.findById(byId.get().getSalesleadId());
                if(byId1.isPresent()){
                    SalesleadOnly salesleadOnly = byId1.get();
                    // 如果商机是当前报价的商机，则更新商机的报价审核状态 发送商机报价消息
                    if(Objects.equals(salesleadOnly.getCurrentQuotationId(), quotationId)){
                        salesleadOnlyRepository.updateQuotationAuditStatusAndQuotationAuditMarkById(taskStatus, reason,salesleadOnly.getId());

                        // 如果都审核通过了，则发送商机报价消息
                        if(Objects.equals(taskStatus,QuotationAuditStatusEnums.ACCEPT.getStatus())){
//                            salesleadServiceImpl.sendNotifyWhenQuotationedBySalesleadId(salesleadOnly.getId());
                        }
                    }


                }
            }
            projectQuotationRepository.updateQuotationAuditStatusAndQuotationAuditMarkById(taskStatus, reason, quotationId);

        }
    }

    @Override
    @Transactional
    public void returnTask(BpmTaskReturnReqVO reqVO) {

        ProcessInstance instance = taskService.getProcessInstanceByTaskId(reqVO.getId());

        String processDefinitionKey = instance.getProcessDefinitionKey();

        if (Objects.equals(processDefinitionKey, PROJECT_PROCUREMENT_AUDIT) || Objects.equals(processDefinitionKey, OFFICE_PROCUREMENT_AUDIT) || Objects.equals(processDefinitionKey, LAB_PROCUREMENT_AUDIT)) {
            if (reqVO.getRefId() == null || reqVO.getTaskStatus() == null) {
                throw exception(BPM_PARAMS_ERROR);
            }
            procurementRepository.updateStatusById(reqVO.getRefId(), reqVO.getTaskStatus());
        }

        if (Objects.equals(processDefinitionKey, PROCUREMENT_PURCHASE_CONTRACT_AUDIT)) {
            if (reqVO.getRefId() == null || reqVO.getTaskStatus() == null) {
                throw exception(BPM_PARAMS_ERROR);
            }
            purchaseContractRepository.updateStatusById(reqVO.getTaskStatus(), reqVO.getRefId());
        }

        taskService.returnTask(getLoginUserId(), reqVO);
    }

    @Override
    @Transactional
    public void rejectTask(BpmTaskRejectReqVO reqVO) {

        ProcessInstance instance = taskService.getProcessInstanceByTaskId(reqVO.getId());

        String processDefinitionKey = instance.getProcessDefinitionKey();

        if (reqVO.getRefId() != null) {
            // 如果是三个采购单：项目、实验室、行政
            if (Objects.equals(processDefinitionKey, PROJECT_PROCUREMENT_AUDIT) || Objects.equals(processDefinitionKey, OFFICE_PROCUREMENT_AUDIT) || Objects.equals(processDefinitionKey, LAB_PROCUREMENT_AUDIT)) {
                procurementRepository.updateStatusById(reqVO.getRefId(), ProcurementStatusEnums.REJECT.getStatus());
                procurementItemRepository.updateStatusByProcurementId(ProcurementItemStatusEnums.REJECT_PROCUREMENT.getStatus(), reqVO.getRefId());
            }

            // 如果是购销合同
            if (Objects.equals(processDefinitionKey, PROCUREMENT_PURCHASE_CONTRACT_AUDIT)) {
                purchaseContractRepository.updateStatusById(PurchaseContractStatusEnums.REJECT.getStatus(), reqVO.getRefId());
                procurementItemRepository.updateStatusByPurchaseContractId(ProcurementItemStatusEnums.REJECT_ORDER.getStatus(), reqVO.getRefId());
            }

            // 商机报价审核
            if(Objects.equals(processDefinitionKey,QUOTATION_AUDIT)){
                processQuotationStatus(QuotationAuditStatusEnums.REJECT.getStatus(),reqVO.getReason(), reqVO.getRefId());

            }

            // 如果是项目状态变更
            if(Objects.equals(processDefinitionKey,PROJECT_STATUS_CHANGE)){
                projectApprovalServiceImpl.updateProjectApprovalByResultAndId(BpmTaskStatustEnum.REJECT.getStatus().toString(),reqVO.getReason(),reqVO.getRefId());
            }
        }


        taskService.rejectTask(getLoginUserId(), reqVO);
    }

    @Override
    @Transactional
    public void cancelInstance(BpmProcessInstanceCancelReqVO reqVO) {
        // 校验是否有人审批过
        long count = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(reqVO.getId())
                .finished()
                .count();
        if (count > 0) {
            throw exception(BPM_INSTANCE_CANCEL_FAIL_APPROVED);
        }

        ProcessInstance processInstance = processInstanceService.getProcessInstance(reqVO.getId());
        String processDefinitionKey = processInstance.getProcessDefinitionKey();
        boolean canCancel = (processDefinitionKey.contains("PROCUREMENT")&&!processDefinitionKey.contains("PURCHASE_CONTRACT")) || processDefinitionKey.contains(PROJECT_OUTBOUND_APPLY) || processDefinitionKey.contains(QUOTATION_AUDIT);
        if(!canCancel){
            throw  exception(BPM_CAN_NOT_CANCEL);
        }

        if (processDefinitionKey.contains("PROCUREMENT")&&!processDefinitionKey.contains("PURCHASE_CONTRACT")) {
            procurementRepository.updateStatusById(reqVO.getRefId(), ProcurementStatusEnums.CANCEL.getStatus());
            procurementItemRepository.updateStatusByProcurementId(ProcurementItemStatusEnums.CANCEL.getStatus(), reqVO.getRefId());
        }

        if (processDefinitionKey.contains(PROJECT_OUTBOUND_APPLY)) {
            projectOnlyRepository.updateProcessInstanceIdById( null, reqVO.getRefId());
        }
        // 如果是报价审批
        if(processDefinitionKey.contains(QUOTATION_AUDIT)){
            projectQuotationRepository.updateAuditProcessIdAndAuditStatusById(null,null,reqVO.getRefId());
        }

        processInstanceService.cancelProcessInstance(getLoginUserId(), reqVO);
    }

    /**
     * 从 BPMN 定义中获取任务的 nextStatus 参数
     * 这个方法模拟前端从 BPMN XML 中解析 nextStatus 的逻辑
     * 
     * @param processDefinitionId 流程定义ID
     * @param taskDefinitionKey 任务定义Key
     * @return nextStatus 值，如果未定义则返回 null
     */
    private String getTaskNextStatusFromBpmn(String processDefinitionId, String taskDefinitionKey) {
        log.info("[getTaskNextStatusFromBpmn][开始获取，processDefinitionId：{}，taskDefinitionKey：{}]", 
                processDefinitionId, taskDefinitionKey);
        
        try {
            // 1. 获取 BPMN 模型
            BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
            if (bpmnModel == null) {
                log.warn("[getTaskNextStatusFromBpmn][流程定义{}的BPMN模型不存在]", processDefinitionId);
                return null;
            }
            log.info("[getTaskNextStatusFromBpmn][成功获取BPMN模型]");
            
            // 2. 获取任务节点
            FlowElement flowElement = bpmnModel.getMainProcess().getFlowElement(taskDefinitionKey);
            if (flowElement == null) {
                log.warn("[getTaskNextStatusFromBpmn][任务节点{}不存在]", taskDefinitionKey);
                return null;
            }
            log.info("[getTaskNextStatusFromBpmn][找到任务节点，类型：{}，名称：{}]", 
                    flowElement.getClass().getSimpleName(), flowElement.getName());
            
            if (!(flowElement instanceof UserTask)) {
                log.warn("[getTaskNextStatusFromBpmn][任务{}不是UserTask类型，实际类型：{}]", 
                        taskDefinitionKey, flowElement.getClass().getName());
                return null;
            }
            
            UserTask userTask = (UserTask) flowElement;
            
            // 3. 从自定义属性中获取 nextStatus
            // 尝试多个可能的命名空间
            String[] namespaces = {
                "http://flowable.org/bpmn",
                "http://activiti.org/bpmn",
                "http://camunda.org/schema/1.0/bpmn"
            };
            
            String nextStatus = null;
            for (String namespace : namespaces) {
                nextStatus = userTask.getAttributeValue(namespace, "nextStatus");
                if (nextStatus != null) {
                    log.info("[getTaskNextStatusFromBpmn][在命名空间{}中找到nextStatus：{}]", namespace, nextStatus);
                    break;
                }
            }
            
            // 如果还是null，打印所有属性
            if (nextStatus == null) {
                log.warn("[getTaskNextStatusFromBpmn][未找到nextStatus属性，打印所有属性]");
                if (userTask.getAttributes() != null && !userTask.getAttributes().isEmpty()) {
                    userTask.getAttributes().forEach((key, values) -> {
                        log.info("[getTaskNextStatusFromBpmn][属性 {} = {}]", key, values);
                    });
                } else {
                    log.warn("[getTaskNextStatusFromBpmn][任务没有任何自定义属性]");
                }
            }
            
            log.info("[getTaskNextStatusFromBpmn][最终结果，任务{}的nextStatus：{}]", taskDefinitionKey, nextStatus);
            return nextStatus;
            
        } catch (Exception e) {
            log.error("[getTaskNextStatusFromBpmn][获取任务{}的nextStatus失败]", taskDefinitionKey, e);
            return null;
        }
    }

}