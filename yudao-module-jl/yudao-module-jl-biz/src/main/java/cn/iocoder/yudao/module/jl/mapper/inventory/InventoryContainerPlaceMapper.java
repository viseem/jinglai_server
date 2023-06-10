package cn.iocoder.yudao.module.jl.mapper.inventory;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryContainerPlace;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface InventoryContainerPlaceMapper {
    InventoryContainerPlace toEntity(InventoryContainerPlaceCreateReqVO dto);

    InventoryContainerPlace toEntity(InventoryContainerPlaceUpdateReqVO dto);

    InventoryContainerPlaceRespVO toDto(InventoryContainerPlace entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    InventoryContainerPlace partialUpdate(InventoryContainerPlaceUpdateReqVO dto, @MappingTarget InventoryContainerPlace entity);

    List<InventoryContainerPlaceExcelVO> toExcelList(List<InventoryContainerPlace> list);

    PageResult<InventoryContainerPlaceRespVO> toPage(PageResult<InventoryContainerPlace> page);
}