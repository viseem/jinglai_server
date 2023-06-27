package cn.iocoder.yudao.module.jl.mapper.project;

import java.util.*;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.SupplyPickupItem;
import org.mapstruct.*;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SupplyPickupItemMapper {
    SupplyPickupItem toEntity(SupplyPickupItemCreateReqVO dto);

    SupplyPickupItem toEntity(SupplyPickupItemUpdateReqVO dto);

    SupplyPickupItemRespVO toDto(SupplyPickupItem entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SupplyPickupItem partialUpdate(SupplyPickupItemUpdateReqVO dto, @MappingTarget SupplyPickupItem entity);

    List<SupplyPickupItemExcelVO> toExcelList(List<SupplyPickupItem> list);

    PageResult<SupplyPickupItemRespVO> toPage(PageResult<SupplyPickupItem> page);

}