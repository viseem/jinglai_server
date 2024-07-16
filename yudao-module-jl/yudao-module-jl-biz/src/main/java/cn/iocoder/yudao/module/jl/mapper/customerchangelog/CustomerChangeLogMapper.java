package cn.iocoder.yudao.module.jl.mapper.customerchangelog;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.customerchangelog.vo.*;
import cn.iocoder.yudao.module.jl.entity.customerchangelog.CustomerChangeLog;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerChangeLogMapper {
    CustomerChangeLog toEntity(CustomerChangeLogCreateReqVO dto);

    CustomerChangeLog toEntity(CustomerChangeLogUpdateReqVO dto);

    CustomerChangeLogRespVO toDto(CustomerChangeLog entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CustomerChangeLog partialUpdate(CustomerChangeLogUpdateReqVO dto, @MappingTarget CustomerChangeLog entity);

    List<CustomerChangeLogExcelVO> toExcelList(List<CustomerChangeLog> list);

    PageResult<CustomerChangeLogRespVO> toPage(PageResult<CustomerChangeLog> page);
}