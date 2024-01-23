package cn.iocoder.yudao.module.jl.mapper.shipwarehouse;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.shipwarehouse.vo.*;
import cn.iocoder.yudao.module.jl.entity.shipwarehouse.ShipWarehouse;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ShipWarehouseMapper {
    ShipWarehouse toEntity(ShipWarehouseCreateReqVO dto);

    ShipWarehouse toEntity(ShipWarehouseUpdateReqVO dto);

    ShipWarehouseRespVO toDto(ShipWarehouse entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ShipWarehouse partialUpdate(ShipWarehouseUpdateReqVO dto, @MappingTarget ShipWarehouse entity);

    List<ShipWarehouseExcelVO> toExcelList(List<ShipWarehouse> list);

    PageResult<ShipWarehouseRespVO> toPage(PageResult<ShipWarehouse> page);
}