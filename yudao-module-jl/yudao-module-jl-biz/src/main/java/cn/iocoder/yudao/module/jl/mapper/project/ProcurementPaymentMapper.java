package cn.iocoder.yudao.module.jl.mapper.project;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.ProcurementPayment;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProcurementPaymentMapper {
    ProcurementPayment toEntity(ProcurementPaymentCreateReqVO dto);

    ProcurementPayment toEntity(ProcurementPaymentUpdateReqVO dto);

    ProcurementPaymentRespVO toDto(ProcurementPayment entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProcurementPayment partialUpdate(ProcurementPaymentUpdateReqVO dto, @MappingTarget ProcurementPayment entity);

    List<ProcurementPaymentExcelVO> toExcelList(List<ProcurementPayment> list);

    PageResult<ProcurementPaymentRespVO> toPage(PageResult<ProcurementPayment> page);
}