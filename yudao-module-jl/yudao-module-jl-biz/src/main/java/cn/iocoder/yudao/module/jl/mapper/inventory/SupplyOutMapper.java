package cn.iocoder.yudao.module.jl.mapper.inventory;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.SupplyOut;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SupplyOutMapper {
    SupplyOut toEntity(SupplyOutCreateReqVO dto);
    SupplyOut toEntity(SupplyOutSaveReqVO dto);
    SupplyOut toEntity(SupplyOutUpdateReqVO dto);

    SupplyOutRespVO toDto(SupplyOut entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SupplyOut partialUpdate(SupplyOutUpdateReqVO dto, @MappingTarget SupplyOut entity);

    List<SupplyOutExcelVO> toExcelList(List<SupplyOut> list);

    PageResult<SupplyOutRespVO> toPage(PageResult<SupplyOut> page);
}