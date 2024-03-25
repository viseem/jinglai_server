package cn.iocoder.yudao.module.jl.service.statistic;

import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.*;
import cn.iocoder.yudao.module.jl.enums.*;
import cn.iocoder.yudao.module.jl.repository.contractfundlog.ContractFundLogRepository;
import cn.iocoder.yudao.module.jl.repository.contractinvoicelog.ContractInvoiceLogRepository;
import cn.iocoder.yudao.module.jl.repository.crm.CustomerRepository;
import cn.iocoder.yudao.module.jl.repository.crm.SalesleadRepository;
import cn.iocoder.yudao.module.jl.repository.financepayment.FinancePaymentRepository;
import cn.iocoder.yudao.module.jl.repository.inventory.ProductInRepository;
import cn.iocoder.yudao.module.jl.repository.inventory.ProductSendRepository;
import cn.iocoder.yudao.module.jl.repository.inventory.SupplyOutRepository;
import cn.iocoder.yudao.module.jl.repository.project.*;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;

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

    @Resource
    private ContractFundLogRepository contractFundLogRepository;

    @Resource
    private ContractInvoiceLogRepository contractInvoiceLogRepository;

    private final FinancePaymentRepository financePaymentRepository;

    public WorkstationServiceImpl(FinancePaymentRepository financePaymentRepository) {
        this.financePaymentRepository = financePaymentRepository;
    }

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

        //自己的未处理的反馈数量
        resp.setNotProcessFeedbackCount(getNotProcessFeedbackCount());
        return resp;
    }

    private Integer getNotProcessFeedbackCount(){
        return projectFeedbackRepository.countByStatusNotAndUserId(ProjectFeedbackEnums.PROCESSED.getStatus(),getLoginUserId());
    }

    @Override
    public WorkstationProjectCountStatsResp getProjectCountStats(){
        WorkstationProjectCountStatsResp resp = new WorkstationProjectCountStatsResp();

        //自己的未完成的项目数量
        Integer projectNotCompleteCount = projectRepository.countByManagerIdAndStageNot(getLoginUserId(), ProjectStageEnums.OUTED.getStatus());
        resp.setNotCompleteProjectCount(projectNotCompleteCount);

        //自己的未处理的反馈数量
        resp.setNotProcessFeedbackCount(getNotProcessFeedbackCount());

        //自己的未报价的线索数量
        Integer notQuotationCount = salesleadRepository.countByManagerIdAndStatus(getLoginUserId(), Integer.valueOf(SalesLeadStatusEnums.QUOTATION.getStatus()));
        resp.setNotQuotationCount(notQuotationCount);

        //自己的未完成的任务数量
        Integer notCompleteTaskCount = projectCategoryRepository.countByOperatorIdAndStageNot(getLoginUserId(), ProjectCategoryStatusEnums.COMPLETE.getStatus());
        resp.setNotCompleteTaskCount(notCompleteTaskCount);
        return resp;
    }

    @Override
    public WorkstationExpCountStatsResp getExpCountStats(){
        WorkstationExpCountStatsResp resp = new WorkstationExpCountStatsResp();

        //自己的未完成的任务数量
        Integer notCompleteTaskCount = projectCategoryRepository.countByOperatorIdAndStageNot(getLoginUserId(), ProjectCategoryStatusEnums.COMPLETE.getStatus());
        resp.setNotCompleteTaskCount(notCompleteTaskCount);

        //自己的未处理的反馈数量
        resp.setNotProcessFeedbackCount(getNotProcessFeedbackCount());

        return resp;
    }

    @Override
    public WorkstationFinanceCountStatsResp getFinanceCountStats(){
        WorkstationFinanceCountStatsResp resp = new WorkstationFinanceCountStatsResp();

        // 全部的 合同收款 未核验的
        Integer contractFundNotAuditCount = contractFundLogRepository.countByStatusNot(ContractFundStatusEnums.AUDITED.getStatus());
        resp.setContractFundNotAuditCount(contractFundNotAuditCount);

        //全部的 合同发票 未核验的

        Integer contractInvoiceNotAuditCount = contractInvoiceLogRepository.countByStatusNotOrPriceStatusNot(ContractInvoiceStatusEnums.INVOICED.getStatus(),ContractInvoiceStatusEnums.RECEIVED_ALL.getStatus());
        resp.setContractInvoiceNotAuditCount(contractInvoiceNotAuditCount);

        //全部的 未打款采购单数量
//        Integer procurementNotPayCount = procurementRepository.countByStatus(ProcurementStatusEnums.WAITING_FINANCE_CONFIRM.getStatus());
//        resp.setProcurementNotPayCount(procurementNotPayCount);

        //全部的 为打款的打款单数量
        Integer integer = financePaymentRepository.countByAuditStatusNot(FinancePaymentEnums.PAYED.getStatus());
        resp.setFinancePaymentNotPayCount(integer);

        //自己的未处理的反馈数量
        resp.setNotProcessFeedbackCount(getNotProcessFeedbackCount());

        return resp;
    }

    @Override
    public WorkstationWarehouseCountStatsResp getWarehouseCountStats(){
        WorkstationWarehouseCountStatsResp resp = new WorkstationWarehouseCountStatsResp();

        //--------全部的 未签收的单子数量
        //未签收的采购单数量
        Integer waitingCheckInProcurementCount = procurementRepository.countByWaitCheckIn(true);
        resp.setWaitingCheckInProcurementCount(waitingCheckInProcurementCount);
        //未签收的寄来单数量
        Integer waitingCheckInSendInCount = supplySendInRepository.countByWaitCheckIn(true);
        resp.setWaitingCheckInSendInCount(waitingCheckInSendInCount);
        //未签收的提货单数量
        Integer waitingCheckInPickupCount = supplyPickupRepository.countByWaitCheckIn(true);
        resp.setWaitingCheckInPickupCount(waitingCheckInPickupCount);

        //-----------------全部的 未入库的单子数量
        //未入库的采购单数量
        Integer waitingInProcurementCount = procurementRepository.countByWaitStoreIn(true);
        resp.setWaitingInProcurementCount(waitingInProcurementCount);
        //未入库的寄来单数量
        Integer waitingInSendInCount = supplySendInRepository.countByWaitStoreIn(true);
        resp.setWaitingInSendInCount(waitingInSendInCount);
        //未入库的提货单数量
        Integer waitingInPickupCount = supplyPickupRepository.countByWaitStoreIn(true);
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

        //自己的未处理的反馈数量
        resp.setNotProcessFeedbackCount(getNotProcessFeedbackCount());

        return resp;
    }
}