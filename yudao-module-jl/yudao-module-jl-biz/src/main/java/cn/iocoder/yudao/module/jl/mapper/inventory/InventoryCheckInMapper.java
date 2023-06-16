package cn.iocoder.yudao.module.jl.mapper.inventory;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryCheckIn;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface InventoryCheckInMapper {
    InventoryCheckIn toEntity(InventoryCheckInCreateReqVO dto);

    InventoryCheckIn toEntity(InventoryCheckInUpdateReqVO dto);

    InventoryCheckInRespVO toDto(InventoryCheckIn entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    InventoryCheckIn partialUpdate(InventoryCheckInUpdateReqVO dto, @MappingTarget InventoryCheckIn entity);

    List<InventoryCheckInExcelVO> toExcelList(List<InventoryCheckIn> list);

    PageResult<InventoryCheckInRespVO> toPage(PageResult<InventoryCheckIn> page);
}