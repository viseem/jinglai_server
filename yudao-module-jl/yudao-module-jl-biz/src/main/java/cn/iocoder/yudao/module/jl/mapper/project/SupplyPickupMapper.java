package cn.iocoder.yudao.module.jl.mapper.project;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.SupplyPickup;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SupplyPickupMapper {
    SupplyPickup toEntity(SupplyPickupCreateReqVO dto);

    SupplyPickup toEntity(SupplyPickupUpdateReqVO dto);

    SupplyPickup toEntity(SupplyPickupSaveReqVO dto);

    SupplyPickupRespVO toDto(SupplyPickup entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SupplyPickup partialUpdate(SupplyPickupUpdateReqVO dto, @MappingTarget SupplyPickup entity);

    List<SupplyPickupExcelVO> toExcelList(List<SupplyPickup> list);

    PageResult<SupplyPickupRespVO> toPage(PageResult<SupplyPickup> page);
}