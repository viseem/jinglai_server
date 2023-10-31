package cn.iocoder.yudao.module.jl.service.statistic;

import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.chart.*;
import cn.iocoder.yudao.module.jl.entity.contractfundlog.ContractFundLog;
import cn.iocoder.yudao.module.jl.entity.contractinvoicelog.ContractInvoiceLog;
import cn.iocoder.yudao.module.jl.entity.crm.Saleslead;
import cn.iocoder.yudao.module.jl.entity.project.ProjectConstractOnly;
import cn.iocoder.yudao.module.jl.entity.project.ProjectFundOnly;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
import cn.iocoder.yudao.module.jl.repository.contractfundlog.ContractFundLogRepository;
import cn.iocoder.yudao.module.jl.repository.contractinvoicelog.ContractInvoiceLogRepository;
import cn.iocoder.yudao.module.jl.repository.crm.SalesleadRepository;
import cn.iocoder.yudao.module.jl.repository.project.*;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.persistence.criteria.Predicate;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Service
@Validated
public class ChartServiceImpl implements ChartService {

    @Resource
    ContractFundLogRepository contractFundLogRepository;

    @Resource
    ContractInvoiceLogRepository contractInvoiceLogRepository;

    @Resource
    ProjectConstractOnlyRepository projectConstractOnlyRepository;

    @Resource
    SalesleadRepository salesleadRepository;

    @Resource
    ProjectSimpleRepository projectSimpleRepository;



    @Override
    public ChartRefundStatsResp getRefundStats(ChartRefundStatsReqVO reqVO) {
        Specification<ContractFundLog> spec = getCommonSpec(reqVO,"salesId","paidTime");

        List<ContractFundLog> fundList = contractFundLogRepository.findAll(spec);

        ChartRefundStatsResp resp = new ChartRefundStatsResp();
        resp.setFundList(fundList);
        return resp;
    }

    @Override
    public ChartInvoiceStatsResp getInvoiceStats(ChartInvoiceStatsReqVO reqVO) {
        Specification<ContractInvoiceLog> spec = getCommonSpec(reqVO,"salesId","date");

        List<ContractInvoiceLog> list = contractInvoiceLogRepository.findAll(spec);

        ChartInvoiceStatsResp resp = new ChartInvoiceStatsResp();
        resp.setInvoiceList(list);
        return resp;
    }

    @Override
    public ChartSalesleadStatsResp getSalesleadStats(ChartSalesleadStatsReqVO reqVO) {
        Specification<Saleslead> spec = getCommonSpec(reqVO,null,"createTime");
        List<Saleslead> list = salesleadRepository.findAll(spec);
        ChartSalesleadStatsResp resp = new ChartSalesleadStatsResp();
        resp.setList(list);
        return resp;
    }

    @Override
    public ChartProjectStatsResp getProjectStats(ChartProjectStatsReqVO reqVO) {
        Specification<ProjectSimple> spec = getCommonSpec(reqVO,null,"createTime");
        List<ProjectSimple> all = projectSimpleRepository.findAll(spec);
        ChartProjectStatsResp resp = new ChartProjectStatsResp();
        resp.setList(all);
        return resp;
    }

    @Override
    public ChartContractStatsResp getContractStats(ChartContractStatsReqVO reqVO) {
        Specification<ProjectConstractOnly> spec = getCommonSpec(reqVO,null,null);
        List<ProjectConstractOnly> all = projectConstractOnlyRepository.findAll(spec);
        ChartContractStatsResp resp = new ChartContractStatsResp();
        resp.setList(all);
        return resp;
    }

    @NotNull
    private <T> Specification<T> getCommonSpec(ChartBaseReqVO reqVO, String attributeKey,String timeKey) {


        Specification<T> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            //如果传入了年份，并且不是今年
            if(reqVO.getYear()!=null&&reqVO.getYear().getYear()!=LocalDateTime.now().getYear()){
                reqVO.setStartTime(reqVO.getYear().with(TemporalAdjusters.firstDayOfYear()).withHour(0).withMinute(0).withSecond(0));
                reqVO.setEndTime(reqVO.getYear().with(TemporalAdjusters.lastDayOfYear()).withHour(23).withMinute(59).withSecond(59));
            }

/*            if(reqVO.getIsToNow()){
                //小于等于currentDay
                predicates.add(cb.lessThanOrEqualTo(root.get(timeKey==null?"createTime":timeKey), currentDay));
            }else{
                if(reqVO.getStartTime()==null){
                    LocalDateTime firstDayOfMonth = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth());
                    predicates.add(cb.between(root.get(timeKey==null?"createTime":timeKey), firstDayOfMonth, currentDay));
                }else{
                    predicates.add(cb.between(root.get(timeKey==null?"createTime":timeKey), reqVO.getStartTime(), currentDay));
                }
            }*/
            predicates.add(cb.between(root.get(timeKey==null?"createTime":timeKey), reqVO.getStartTime(), reqVO.getEndTime()));


            if (!reqVO.getIsAllAttribute()) {
                 predicates.add(cb.equal(root.get(attributeKey==null?"creator":attributeKey), getLoginUserId()));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };
        return spec;
    }


}