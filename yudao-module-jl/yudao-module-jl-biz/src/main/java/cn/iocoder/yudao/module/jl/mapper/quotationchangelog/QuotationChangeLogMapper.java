package cn.iocoder.yudao.module.jl.mapper.quotationchangelog;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.quotationchangelog.vo.*;
import cn.iocoder.yudao.module.jl.entity.quotationchangelog.QuotationChangeLog;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface QuotationChangeLogMapper {
    QuotationChangeLog toEntity(QuotationChangeLogCreateReqVO dto);

    QuotationChangeLog toEntity(QuotationChangeLogUpdateReqVO dto);

    QuotationChangeLogRespVO toDto(QuotationChangeLog entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    QuotationChangeLog partialUpdate(QuotationChangeLogUpdateReqVO dto, @MappingTarget QuotationChangeLog entity);

    List<QuotationChangeLogExcelVO> toExcelList(List<QuotationChangeLog> list);

    PageResult<QuotationChangeLogRespVO> toPage(PageResult<QuotationChangeLog> page);
}