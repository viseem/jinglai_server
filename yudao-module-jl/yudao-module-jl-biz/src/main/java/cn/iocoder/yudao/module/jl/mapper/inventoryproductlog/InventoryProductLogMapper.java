package cn.iocoder.yudao.module.jl.mapper.inventoryproductlog;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.inventoryproductlog.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventoryproductlog.InventoryProductLog;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface InventoryProductLogMapper {
    InventoryProductLog toEntity(InventoryProductLogCreateReqVO dto);

    InventoryProductLog toEntity(InventoryProductLogUpdateReqVO dto);

    InventoryProductLogRespVO toDto(InventoryProductLog entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    InventoryProductLog partialUpdate(InventoryProductLogUpdateReqVO dto, @MappingTarget InventoryProductLog entity);

    List<InventoryProductLogExcelVO> toExcelList(List<InventoryProductLog> list);

    PageResult<InventoryProductLogRespVO> toPage(PageResult<InventoryProductLog> page);
}