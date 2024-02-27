package cn.iocoder.yudao.module.jl.mapper.salesgroup;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.salesgroup.vo.*;
import cn.iocoder.yudao.module.jl.entity.salesgroup.SalesGroup;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SalesGroupMapper {
    SalesGroup toEntity(SalesGroupCreateReqVO dto);

    SalesGroup toEntity(SalesGroupUpdateReqVO dto);

    SalesGroupRespVO toDto(SalesGroup entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SalesGroup partialUpdate(SalesGroupUpdateReqVO dto, @MappingTarget SalesGroup entity);

    List<SalesGroupExcelVO> toExcelList(List<SalesGroup> list);

    PageResult<SalesGroupRespVO> toPage(PageResult<SalesGroup> page);
}