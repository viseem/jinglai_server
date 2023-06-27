package cn.iocoder.yudao.module.jl.mapper.inventory;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryStoreIn;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface InventoryStoreInMapper {
    InventoryStoreIn toEntity(InventoryStoreInCreateReqVO dto);

    InventoryStoreIn toEntity(InventoryStoreInUpdateReqVO dto);

    InventoryStoreInRespVO toDto(InventoryStoreIn entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    InventoryStoreIn partialUpdate(InventoryStoreInUpdateReqVO dto, @MappingTarget InventoryStoreIn entity);

    List<InventoryStoreInExcelVO> toExcelList(List<InventoryStoreIn> list);

    PageResult<InventoryStoreInRespVO> toPage(PageResult<InventoryStoreIn> page);
}