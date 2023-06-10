package cn.iocoder.yudao.module.jl.mapper.project;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.SupplyLog;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SupplyLogMapper {
    SupplyLog toEntity(SupplyLogCreateReqVO dto);

    SupplyLog toEntity(SupplyLogUpdateReqVO dto);

    SupplyLogRespVO toDto(SupplyLog entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SupplyLog partialUpdate(SupplyLogUpdateReqVO dto, @MappingTarget SupplyLog entity);

    List<SupplyLogExcelVO> toExcelList(List<SupplyLog> list);

    PageResult<SupplyLogRespVO> toPage(PageResult<SupplyLog> page);
}