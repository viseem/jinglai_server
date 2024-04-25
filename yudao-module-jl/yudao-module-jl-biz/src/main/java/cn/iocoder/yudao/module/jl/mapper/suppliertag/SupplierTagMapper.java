package cn.iocoder.yudao.module.jl.mapper.suppliertag;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.suppliertag.vo.*;
import cn.iocoder.yudao.module.jl.entity.suppliertag.SupplierTag;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SupplierTagMapper {
    SupplierTag toEntity(SupplierTagCreateReqVO dto);

    SupplierTag toEntity(SupplierTagUpdateReqVO dto);

    SupplierTagRespVO toDto(SupplierTag entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SupplierTag partialUpdate(SupplierTagUpdateReqVO dto, @MappingTarget SupplierTag entity);

    List<SupplierTagExcelVO> toExcelList(List<SupplierTag> list);

    PageResult<SupplierTagRespVO> toPage(PageResult<SupplierTag> page);
}