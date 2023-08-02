package cn.iocoder.yudao.module.jl.mapper.inventory;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryStoreOut;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface InventoryStoreOutMapper {
    InventoryStoreOut toEntity(InventoryStoreOutCreateReqVO dto);

    InventoryStoreOut toEntity(InventoryStoreOutUpdateReqVO dto);

    InventoryStoreOutRespVO toDto(InventoryStoreOut entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    InventoryStoreOut partialUpdate(InventoryStoreOutUpdateReqVO dto, @MappingTarget InventoryStoreOut entity);

    List<InventoryStoreOutExcelVO> toExcelList(List<InventoryStoreOut> list);

    PageResult<InventoryStoreOutRespVO> toPage(PageResult<InventoryStoreOut> page);
}