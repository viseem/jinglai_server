package cn.iocoder.yudao.module.jl.mapper.financepayment;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.financepayment.vo.*;
import cn.iocoder.yudao.module.jl.entity.financepayment.FinancePayment;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface FinancePaymentMapper {
    FinancePayment toEntity(FinancePaymentCreateReqVO dto);

    FinancePayment toEntity(FinancePaymentUpdateReqVO dto);

    FinancePaymentRespVO toDto(FinancePayment entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    FinancePayment partialUpdate(FinancePaymentUpdateReqVO dto, @MappingTarget FinancePayment entity);

    List<FinancePaymentExcelVO> toExcelList(List<FinancePayment> list);

    PageResult<FinancePaymentRespVO> toPage(PageResult<FinancePayment> page);
}