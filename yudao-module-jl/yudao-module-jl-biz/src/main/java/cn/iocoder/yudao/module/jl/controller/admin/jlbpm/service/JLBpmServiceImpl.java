package cn.iocoder.yudao.module.jl.controller.admin.jlbpm.service;

import cn.iocoder.yudao.module.bpm.controller.admin.task.vo.instance.BpmProcessInstanceCancelReqVO;
import cn.iocoder.yudao.module.bpm.controller.admin.task.vo.task.BpmTaskApproveReqVO;
import cn.iocoder.yudao.module.bpm.controller.admin.task.vo.task.BpmTaskRejectReqVO;
import cn.iocoder.yudao.module.bpm.controller.admin.task.vo.task.BpmTaskReturnReqVO;
import cn.iocoder.yudao.module.bpm.service.task.BpmProcessInstanceServiceImpl;
import cn.iocoder.yudao.module.bpm.service.task.BpmTaskServiceImpl;
import cn.iocoder.yudao.module.jl.controller.admin.jlbpm.vo.JLBpmTaskReqVO;
import cn.iocoder.yudao.module.jl.entity.crm.SalesleadOnly;
import cn.iocoder.yudao.module.jl.entity.projectquotation.ProjectQuotation;
import cn.iocoder.yudao.module.jl.enums.ProcurementItemStatusEnums;
import cn.iocoder.yudao.module.jl.enums.ProcurementStatusEnums;
import cn.iocoder.yudao.module.jl.enums.PurchaseContractStatusEnums;
import cn.iocoder.yudao.module.jl.enums.QuotationAuditStatusEnums;
import cn.iocoder.yudao.module.jl.repository.crm.SalesleadOnlyRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProcurementItemRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProcurementRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectOnlyRepository;
import cn.iocoder.yudao.module.jl.repository.projectquotation.ProjectQuotationRepository;
import cn.iocoder.yudao.module.jl.repository.purchasecontract.PurchaseContractRepository;
import cn.iocoder.yudao.module.jl.service.crm.SalesleadServiceImpl;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.web.core.util.WebFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.bpm.service.utils.ProcessInstanceKeyConstants.*;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.BPM_PARAMS_ERROR;

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
    private SalesleadOnlyRepository salesleadOnlyRepository;

    @Resource
    private SalesleadServiceImpl salesleadServiceImpl;

    @Resource
    private ProjectQuotationRepository projectQuotationRepository;

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
        }


        BpmTaskApproveReqVO bpmTaskApproveReqVO = new BpmTaskApproveReqVO();
        bpmTaskApproveReqVO.setId(approveReqVO.getId());
        bpmTaskApproveReqVO.setReason(approveReqVO.getReason());
        taskService.approveTask(getLoginUserId(), bpmTaskApproveReqVO);
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
            }

            // 如果是购销合同
            if (Objects.equals(processDefinitionKey, PROCUREMENT_PURCHASE_CONTRACT_AUDIT)) {
                purchaseContractRepository.updateStatusById(PurchaseContractStatusEnums.REJECT.getStatus(), reqVO.getRefId());
            }

            // 商机报价审核
            if(Objects.equals(processDefinitionKey,QUOTATION_AUDIT)){
                processQuotationStatus(QuotationAuditStatusEnums.REJECT.getStatus(),reqVO.getReason(), reqVO.getRefId());

            }
        }


        taskService.rejectTask(getLoginUserId(), reqVO);
    }

    @Override
    @Transactional
    public void cancelInstance(BpmProcessInstanceCancelReqVO reqVO) {

        if (reqVO.getProcessType().equals("PROCUREMENT")) {
            procurementRepository.updateStatusById(reqVO.getRefId(), ProcurementStatusEnums.CANCEL.getStatus());
            procurementItemRepository.updateStatusByProcurementId(ProcurementItemStatusEnums.CANCEL.getStatus(), reqVO.getRefId());
//            procurementRepository.updateProcessInstanceIdById(null, reqVO.getRefId());
        }

        if (reqVO.getProcessType().equals("PROJECT_OUTED")) {
            projectOnlyRepository.updateOutboundApplyResultById( null, reqVO.getRefId());
        }

        processInstanceService.cancelProcessInstance(getLoginUserId(), reqVO);
    }

}