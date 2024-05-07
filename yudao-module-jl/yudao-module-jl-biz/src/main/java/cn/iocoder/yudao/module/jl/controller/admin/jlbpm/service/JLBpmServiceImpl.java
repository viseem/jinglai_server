package cn.iocoder.yudao.module.jl.controller.admin.jlbpm.service;

import cn.iocoder.yudao.module.bpm.controller.admin.task.vo.instance.BpmProcessInstanceCancelReqVO;
import cn.iocoder.yudao.module.bpm.controller.admin.task.vo.task.BpmTaskApproveReqVO;
import cn.iocoder.yudao.module.bpm.controller.admin.task.vo.task.BpmTaskRejectReqVO;
import cn.iocoder.yudao.module.bpm.controller.admin.task.vo.task.BpmTaskReturnReqVO;
import cn.iocoder.yudao.module.bpm.service.task.BpmProcessInstanceServiceImpl;
import cn.iocoder.yudao.module.bpm.service.task.BpmTaskServiceImpl;
import cn.iocoder.yudao.module.jl.controller.admin.jlbpm.vo.JLBpmTaskReqVO;
import cn.iocoder.yudao.module.jl.enums.ProcurementItemStatusEnums;
import cn.iocoder.yudao.module.jl.enums.ProcurementStatusEnums;
import cn.iocoder.yudao.module.jl.enums.PurchaseContractStatusEnums;
import cn.iocoder.yudao.module.jl.repository.project.ProcurementItemRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProcurementRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectOnlyRepository;
import cn.iocoder.yudao.module.jl.repository.purchasecontract.PurchaseContractRepository;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.transaction.Transactional;
import java.util.Objects;

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

    @Override
    @Transactional
    public void approveTask(JLBpmTaskReqVO approveReqVO) {

        ProcessInstance instance = taskService.getProcessInstanceByTaskId(approveReqVO.getId());

        String processDefinitionKey = instance.getProcessDefinitionKey();

        if (Objects.equals(processDefinitionKey, PROJECT_PROCUREMENT_AUDIT) || Objects.equals(processDefinitionKey, OFFICE_PROCUREMENT_AUDIT) || Objects.equals(processDefinitionKey, LAB_PROCUREMENT_AUDIT)) {
            if (approveReqVO.getRefId() == null || approveReqVO.getTaskStatus() == null) {
                throw exception(BPM_PARAMS_ERROR);
            }
            procurementRepository.updateStatusById(approveReqVO.getRefId(), approveReqVO.getTaskStatus());
            if (Objects.equals(approveReqVO.getTaskStatus(), ProcurementStatusEnums.APPROVE.getStatus())) {
                procurementItemRepository.updateStatusByProcurementId(ProcurementItemStatusEnums.APPROVE_PROCUREMENT.getStatus(), approveReqVO.getRefId());
            }
        }

        if (Objects.equals(processDefinitionKey, PROCUREMENT_PURCHASE_CONTRACT_AUDIT)) {
            if (approveReqVO.getRefId() == null || approveReqVO.getTaskStatus() == null) {
                throw exception(BPM_PARAMS_ERROR);
            }
            purchaseContractRepository.updateStatusById(approveReqVO.getTaskStatus(), approveReqVO.getRefId());
            if (Objects.equals(approveReqVO.getTaskStatus(), PurchaseContractStatusEnums.APPROVE.getStatus())) {
                procurementItemRepository.updateStatusByPurchaseContractId(ProcurementItemStatusEnums.ORDERED.getStatus(), approveReqVO.getRefId());
            }
        }

        BpmTaskApproveReqVO bpmTaskApproveReqVO = new BpmTaskApproveReqVO();
        bpmTaskApproveReqVO.setId(approveReqVO.getId());
        bpmTaskApproveReqVO.setReason(approveReqVO.getReason());
        taskService.approveTask(getLoginUserId(), bpmTaskApproveReqVO);
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

        // 如果是三个采购单：项目、实验室、行政
        if (Objects.equals(processDefinitionKey, PROJECT_PROCUREMENT_AUDIT) || Objects.equals(processDefinitionKey, OFFICE_PROCUREMENT_AUDIT) || Objects.equals(processDefinitionKey, LAB_PROCUREMENT_AUDIT)) {
            if (reqVO.getRefId() == null) {
                throw exception(BPM_PARAMS_ERROR);
            }
            procurementRepository.updateStatusById(reqVO.getRefId(), ProcurementStatusEnums.REJECT.getStatus());
        }

        // 如果是购销合同
        if (Objects.equals(processDefinitionKey, PROCUREMENT_PURCHASE_CONTRACT_AUDIT)) {
            if (reqVO.getRefId() == null) {
                throw exception(BPM_PARAMS_ERROR);
            }
            purchaseContractRepository.updateStatusById(PurchaseContractStatusEnums.REJECT.getStatus(), reqVO.getRefId());
        }

        taskService.rejectTask(getLoginUserId(), reqVO);
    }

    @Override
    @Transactional
    public void cancelInstance(BpmProcessInstanceCancelReqVO reqVO) {

        if (reqVO.getProcessType().equals("PROCUREMENT")) {
            procurementRepository.updateStatusById(reqVO.getRefId(), ProcurementStatusEnums.CANCEL.getStatus());
//            procurementRepository.updateProcessInstanceIdById(null, reqVO.getRefId());
        }

        if (reqVO.getProcessType().equals("PROJECT_OUTED")) {
            projectOnlyRepository.updateOutboundApplyResultById( null, reqVO.getRefId());
        }

        processInstanceService.cancelProcessInstance(getLoginUserId(), reqVO);
    }

}