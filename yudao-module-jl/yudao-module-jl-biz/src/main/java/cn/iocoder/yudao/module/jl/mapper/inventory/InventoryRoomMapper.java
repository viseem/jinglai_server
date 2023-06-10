package cn.iocoder.yudao.module.jl.mapper.inventory;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryRoom;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface InventoryRoomMapper {
    InventoryRoom toEntity(InventoryRoomCreateReqVO dto);

    InventoryRoom toEntity(InventoryRoomUpdateReqVO dto);

    InventoryRoomRespVO toDto(InventoryRoom entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    InventoryRoom partialUpdate(InventoryRoomUpdateReqVO dto, @MappingTarget InventoryRoom entity);

    List<InventoryRoomExcelVO> toExcelList(List<InventoryRoom> list);

    PageResult<InventoryRoomRespVO> toPage(PageResult<InventoryRoom> page);
}