package cn.iocoder.yudao.module.jl.mapper.inventory;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.SupplyOutItem;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SupplyOutItemMapper {
    SupplyOutItem toEntity(SupplyOutItemSaveReqVO dto);

    SupplyOutItem toEntity(SupplyOutItemCreateReqVO dto);

    SupplyOutItem toEntity(SupplyOutItemUpdateReqVO dto);

    SupplyOutItemRespVO toDto(SupplyOutItem entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SupplyOutItem partialUpdate(SupplyOutItemUpdateReqVO dto, @MappingTarget SupplyOutItem entity);

    List<SupplyOutItemExcelVO> toExcelList(List<SupplyOutItem> list);

    PageResult<SupplyOutItemRespVO> toPage(PageResult<SupplyOutItem> page);
}