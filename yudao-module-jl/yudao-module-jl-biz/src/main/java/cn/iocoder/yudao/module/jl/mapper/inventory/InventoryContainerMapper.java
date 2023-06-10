package cn.iocoder.yudao.module.jl.mapper.inventory;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryContainer;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface InventoryContainerMapper {
    InventoryContainer toEntity(InventoryContainerCreateReqVO dto);

    InventoryContainer toEntity(InventoryContainerUpdateReqVO dto);

    InventoryContainerRespVO toDto(InventoryContainer entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    InventoryContainer partialUpdate(InventoryContainerUpdateReqVO dto, @MappingTarget InventoryContainer entity);

    List<InventoryContainerExcelVO> toExcelList(List<InventoryContainer> list);

    PageResult<InventoryContainerRespVO> toPage(PageResult<InventoryContainer> page);
}