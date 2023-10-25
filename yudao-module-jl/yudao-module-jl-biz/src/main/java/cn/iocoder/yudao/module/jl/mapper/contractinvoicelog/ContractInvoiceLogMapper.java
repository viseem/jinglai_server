package cn.iocoder.yudao.module.jl.mapper.contractinvoicelog;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.contractinvoicelog.vo.*;
import cn.iocoder.yudao.module.jl.entity.contractinvoicelog.ContractInvoiceLog;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ContractInvoiceLogMapper {
    ContractInvoiceLog toEntity(ContractInvoiceLogCreateReqVO dto);

    ContractInvoiceLog toEntity(ContractInvoiceLogUpdateReqVO dto);

    ContractInvoiceLogRespVO toDto(ContractInvoiceLog entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ContractInvoiceLog partialUpdate(ContractInvoiceLogUpdateReqVO dto, @MappingTarget ContractInvoiceLog entity);

    List<ContractInvoiceLogExcelVO> toExcelList(List<ContractInvoiceLog> list);

    PageResult<ContractInvoiceLogRespVO> toPage(PageResult<ContractInvoiceLog> page);
}