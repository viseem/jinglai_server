package cn.iocoder.yudao.module.jl.service.statistic;

import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.ProjectFeedback;
import cn.iocoder.yudao.module.jl.enums.*;
import cn.iocoder.yudao.module.jl.repository.crm.CustomerRepository;
import cn.iocoder.yudao.module.jl.repository.crm.SalesleadRepository;
import cn.iocoder.yudao.module.jl.repository.inventory.ProductInRepository;
import cn.iocoder.yudao.module.jl.repository.inventory.ProductSendRepository;
import cn.iocoder.yudao.module.jl.repository.inventory.SupplyOutRepository;
import cn.iocoder.yudao.module.jl.repository.project.*;
import org.jetbrains.annotations.Contract;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Service
@Validated
public class WorkstationServiceImpl implements WorkstationService {

    @Resource
    private SalesleadRepository salesleadRepository;

    @Resource
    private ProjectFundRepository projectFundRepository;

    @Resource
    private ProjectRepository projectRepository;

    @Resource
    private ProjectFeedbackRepository projectFeedbackRepository;

    @Resource
    private ProjectCategoryRepository projectCategoryRepository;

    @Resource
    private ProcurementRepository procurementRepository;

    @Resource
    private SupplySendInRepository supplySendInRepository;

    @Resource
    private SupplyPickupRepository supplyPickupRepository;

    @Resource
    private CustomerRepository customerRepository;

    @Resource
    private ProductInRepository productInRepository;

    @Resource
    private SupplyOutRepository supplyOutRepository;

    @Resource
    private ProductSendRepository productSendRepository;

    @Override
    public WorkstationSaleCountStatsResp getSaleCountStats() {
        WorkstationSaleCountStatsResp resp = new WorkstationSaleCountStatsResp();

        // 自己的未转化为项目的销售线索数量
        Integer notToProjectCount = salesleadRepository.countByCreatorAndStatusNot(getLoginUserId(), Integer.valueOf(SalesLeadStatusEnums.ToProject.getStatus()));
        resp.setSalesLeadNotToProjectCount(notToProjectCount);
        // 自己的未收齐的款项数量
        Integer notPayCompleteCount = projectFundRepository.countByCreatorAndStatusNot(getLoginUserId(), ContractFundStatusEnums.COMPLETE.getStatus());
        resp.setProjectFundNotPayCompleteCount(notPayCompleteCount);
        // 自己的未完成的项目数量
        Integer projectNotCompleteCount = projectRepository.countBySalesIdAndStageNot(getLoginUserId(), ProjectStageEnums.OUTED.getStatus());
        resp.setProjectNotCompleteCount(projectNotCompleteCount);
        // 自己的未转化为客户的客户数量
        Integer notToCustomerCount = customerRepository.countByNotToCustomerAndCreator(getLoginUserId());
        resp.setNot2CustomerCount(notToCustomerCount);
        return resp;
    }

    @Override
    public WorkstationProjectCountStatsResp getProjectCountStats(){
        WorkstationProjectCountStatsResp resp = new WorkstationProjectCountStatsResp();

        //自己的未完成的项目数量
        Integer projectNotCompleteCount = projectRepository.countByManagerIdAndStageNot(getLoginUserId(), ProjectStageEnums.OUTED.getStatus());
        resp.setNotCompleteProjectCount(projectNotCompleteCount);

        //自己的未处理的反馈数量
        Integer notProcessFeedbackCount = projectFeedbackRepository.countByStatusNotAndUserId(ProjectFeedbackEnums.PROCESSED.getStatus(),getLoginUserId());
        resp.setNotProcessFeedbackCount(notProcessFeedbackCount);

        //自己的未报价的线索数量
        Integer notQuotationCount = salesleadRepository.countByManagerIdAndStatus(getLoginUserId(), Integer.valueOf(SalesLeadStatusEnums.QUOTATION.getStatus()));
        resp.setNotQuotationCount(notQuotationCount);
        return resp;
    }

    @Override
    public WorkstationExpCountStatsResp getExpCountStats(){
        WorkstationExpCountStatsResp resp = new WorkstationExpCountStatsResp();

        //自己的未完成的任务数量
        Integer notCompleteTaskCount = projectCategoryRepository.countByOperatorIdAndStageNot(getLoginUserId(), ProjectCategoryStatusEnums.COMPLETE.getStatus());
        resp.setNotCompleteTaskCount(notCompleteTaskCount);

        return resp;
    }

    @Override
    public WorkstationFinanceCountStatsResp getFinanceCountStats(){
        WorkstationFinanceCountStatsResp resp = new WorkstationFinanceCountStatsResp();

        // 全部的未收齐的款项数量
        Integer notPayCompleteCount = projectFundRepository.countByStatusNot(ContractFundStatusEnums.COMPLETE.getStatus());
        resp.setProjectFundNotPayCompleteCount(notPayCompleteCount);

        //全部的 未打款采购单数量
        Integer procurementNotPayCount = procurementRepository.countByStatus(ProcurementStatusEnums.WAITING_FINANCE_CONFIRM.getStatus());
        resp.setProcurementNotPayCount(procurementNotPayCount);

        return resp;
    }

    @Override
    public WorkstationWarehouseCountStatsResp getWarehouseCountStats(){
        WorkstationWarehouseCountStatsResp resp = new WorkstationWarehouseCountStatsResp();

        //--------全部的 未签收的单子数量
        //未签收的采购单数量
        Integer waitingCheckInProcurementCount = procurementRepository.countByStatus(ProcurementStatusEnums.WAITING_CHECK_IN.getStatus());
        resp.setWaitingCheckInProcurementCount(waitingCheckInProcurementCount);
        //未签收的寄来单数量
        Integer waitingCheckInSendInCount = supplySendInRepository.countByStatus(ProcurementStatusEnums.WAITING_CHECK_IN.getStatus());
        resp.setWaitingCheckInSendInCount(waitingCheckInSendInCount);
        //未签收的提货单数量
        Integer waitingCheckInPickupCount = supplyPickupRepository.countByStatus(ProcurementStatusEnums.WAITING_CHECK_IN.getStatus());
        resp.setWaitingCheckInPickupCount(waitingCheckInPickupCount);

        //-----------------全部的 未入库的单子数量
        //未入库的采购单数量
        Integer waitingInProcurementCount = procurementRepository.countByStatus(ProcurementStatusEnums.WAITING_IN.getStatus());
        resp.setWaitingInProcurementCount(waitingInProcurementCount);
        //未入库的寄来单数量
        Integer waitingInSendInCount = supplySendInRepository.countByStatus(ProcurementStatusEnums.WAITING_IN.getStatus());
        resp.setWaitingInSendInCount(waitingInSendInCount);
        //未入库的提货单数量
        Integer waitingInPickupCount = supplyPickupRepository.countByStatus(ProcurementStatusEnums.WAITING_IN.getStatus());
        resp.setWaitingInPickupCount(waitingInPickupCount);

        //------------全部的 未入库的申请数量
        Integer waitingProductInCount = productInRepository.countByStatusNot(InventorySupplyInApprovalEnums.ACCEPT.getStatus());
        resp.setWaitingProductInCount(waitingProductInCount);

        //-----未出库的物资申请数量
        Integer waitingSupplyOutCount = supplyOutRepository.countByStatusNot(InventorySupplyOutApprovalEnums.ACCEPT.getStatus());
        resp.setWaitingSupplyOutCount(waitingSupplyOutCount);
        //----未出库的寄送单数量
        Integer waitingSendOutCount = productSendRepository.countByStatusNot(InventorySupplyOutApprovalEnums.ACCEPT.getStatus());
        resp.setWaitingSendOutCount(waitingSendOutCount);

        return resp;
    }
}