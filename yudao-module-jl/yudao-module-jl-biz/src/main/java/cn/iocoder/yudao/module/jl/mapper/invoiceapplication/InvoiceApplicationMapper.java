package cn.iocoder.yudao.module.jl.mapper.invoiceapplication;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.invoiceapplication.vo.*;
import cn.iocoder.yudao.module.jl.entity.invoiceapplication.InvoiceApplication;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface InvoiceApplicationMapper {
    InvoiceApplication toEntity(InvoiceApplicationCreateReqVO dto);

    InvoiceApplication toEntity(InvoiceApplicationUpdateReqVO dto);

    InvoiceApplicationRespVO toDto(InvoiceApplication entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    InvoiceApplication partialUpdate(InvoiceApplicationUpdateReqVO dto, @MappingTarget InvoiceApplication entity);

    List<InvoiceApplicationExcelVO> toExcelList(List<InvoiceApplicationExcelRowItemVO> list);

    PageResult<InvoiceApplicationRespVO> toPage(PageResult<InvoiceApplication> page);
}