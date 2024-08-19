package cn.iocoder.yudao.module.jl.controller.admin.jlbpm.service;

import cn.iocoder.yudao.module.bpm.controller.admin.task.vo.instance.BpmProcessInstanceCancelReqVO;
import cn.iocoder.yudao.module.bpm.controller.admin.task.vo.task.BpmTaskApproveReqVO;
import cn.iocoder.yudao.module.bpm.controller.admin.task.vo.task.BpmTaskRejectReqVO;
import cn.iocoder.yudao.module.bpm.controller.admin.task.vo.task.BpmTaskReturnReqVO;
import cn.iocoder.yudao.module.bpm.enums.message.BpmMessageEnum;
import cn.iocoder.yudao.module.bpm.framework.flowable.core.enums.BpmTaskStatustEnum;
import cn.iocoder.yudao.module.bpm.service.task.BpmProcessInstanceServiceImpl;
import cn.iocoder.yudao.module.bpm.service.task.BpmTaskServiceImpl;
import cn.iocoder.yudao.module.jl.controller.admin.jlbpm.vo.JLBpmTaskReqVO;
import cn.iocoder.yudao.module.jl.entity.commontask.CommonTask;
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
import cn.iocoder.yudao.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.*;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.web.core.util.WebFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.bpm.service.utils.ProcessInstanceKeyConstants.*;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;

/**
 * 项目的实验名目 Service 实现类
 */
@Service
@Validated
public class JLBpmServiceImpl implements JLBpmService {

    @Resource
    private BpmTaskServiceImpl taskService;

    @Resource
    private BpmProcessInstanceServiceImpl processInstanceService;

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

        ProcessInstance instance = taskService.getProcessInstanceByTaskId(approveReqVO.getId());

        String processDefinitionKey = instance.getProcessDefinitionKey();
        if (approveReqVO.getRefId() != null && approveReqVO.getTaskStatus() != null) {
            // 采购
            if (Objects.equals(processDefinitionKey, PROJECT_PROCUREMENT_AUDIT) || Objects.equals(processDefinitionKey, OFFICE_PROCUREMENT_AUDIT) || Objects.equals(processDefinitionKey, LAB_PROCUREMENT_AUDIT)) {

                procurementRepository.updateStatusById(approveReqVO.getRefId(), approveReqVO.getTaskStatus());
                if (Objects.equals(approveReqVO.getTaskStatus(), ProcurementStatusEnums.APPROVE.getStatus())) {
                    procurementItemRepository.updateStatusByProcurementId(ProcurementItemStatusEnums.APPROVE_PROCUREMENT.getStatus(), approveReqVO.getRefId());
                    // 更新采购单的同意时间
                    procurementRepository.updateAcceptTimeById(LocalDateTime.now(), approveReqVO.getRefId());
                    procurementItemRepository.updatePurchaseAcceptTimeByProcurementId(LocalDateTime.now(), approveReqVO.getRefId());
                }
            }

            // 购销合同
            if (Objects.equals(processDefinitionKey, PROCUREMENT_PURCHASE_CONTRACT_AUDIT)) {

                purchaseContractRepository.updateStatusById(approveReqVO.getTaskStatus(), approveReqVO.getRefId());
                if (Objects.equals(approveReqVO.getTaskStatus(), PurchaseContractStatusEnums.APPROVE.getStatus())) {
                    procurementItemRepository.updateStatusByPurchaseContractId(ProcurementItemStatusEnums.ORDERED.getStatus(), approveReqVO.getRefId());
                    // 更新购销合同的同意时间
                    purchaseContractRepository.updateAcceptTimeById(LocalDateTime.now(), approveReqVO.getRefId());
                    procurementItemRepository.updateContractAcceptTimeByPurchaseContractId(LocalDateTime.now(), approveReqVO.getRefId());
                }
            }

            // 商机报价审核
            if(Objects.equals(processDefinitionKey,QUOTATION_AUDIT)){

                processQuotationStatus(approveReqVO.getTaskStatus(),approveReqVO.getReason(), approveReqVO.getRefId());

            }

            // 如果是项目状态变更
            if(Objects.equals(processDefinitionKey,PROJECT_STATUS_CHANGE)){
                processProjectStatusChangeBpm(approveReqVO);
            }
        }


        BpmTaskApproveReqVO bpmTaskApproveReqVO = new BpmTaskApproveReqVO();
        bpmTaskApproveReqVO.setId(approveReqVO.getId());
        bpmTaskApproveReqVO.setReason(approveReqVO.getReason());
        taskService.approveTask(getLoginUserId(), bpmTaskApproveReqVO);
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
                            salesleadServiceImpl.sendNotifyWhenQuotationedBySalesleadId(salesleadOnly.getId());
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
        ProcessInstance processInstance = processInstanceService.getProcessInstance(reqVO.getId());
        String processDefinitionKey = processInstance.getProcessDefinitionKey();

        boolean canCancel = (processDefinitionKey.contains("PROCUREMENT")&&!processDefinitionKey.contains("PURCHASE_CONTRACT")) || reqVO.getProcessType().equals(PROJECT_OUTED) || processDefinitionKey.contains(QUOTATION_AUDIT);
        if(!canCancel){
            throw  exception(BPM_CAN_NOT_CANCEL);
        }

        if (processDefinitionKey.contains("PROCUREMENT")&&!processDefinitionKey.contains("PURCHASE_CONTRACT")) {
            procurementRepository.updateStatusById(reqVO.getRefId(), ProcurementStatusEnums.CANCEL.getStatus());
            procurementItemRepository.updateStatusByProcurementId(ProcurementItemStatusEnums.CANCEL.getStatus(), reqVO.getRefId());
        }

        if (reqVO.getProcessType().equals(PROJECT_OUTED)) {
            projectOnlyRepository.updateOutboundApplyResultById( null, reqVO.getRefId());
        }
        // 如果是报价审批
        if(processDefinitionKey.contains(QUOTATION_AUDIT)){
            projectQuotationRepository.updateAuditProcessIdAndAuditStatusById(null,null,reqVO.getRefId());
        }

        processInstanceService.cancelProcessInstance(getLoginUserId(), reqVO);
    }

}