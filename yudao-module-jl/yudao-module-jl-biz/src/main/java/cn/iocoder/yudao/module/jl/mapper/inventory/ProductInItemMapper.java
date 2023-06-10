package cn.iocoder.yudao.module.jl.mapper.inventory;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.ProductInItem;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductInItemMapper {
    ProductInItem toEntity(ProductInItemCreateReqVO dto);

    ProductInItem toEntity(ProductInItemUpdateReqVO dto);

    ProductInItemRespVO toDto(ProductInItem entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProductInItem partialUpdate(ProductInItemUpdateReqVO dto, @MappingTarget ProductInItem entity);

    List<ProductInItemExcelVO> toExcelList(List<ProductInItem> list);

    PageResult<ProductInItemRespVO> toPage(PageResult<ProductInItem> page);
}