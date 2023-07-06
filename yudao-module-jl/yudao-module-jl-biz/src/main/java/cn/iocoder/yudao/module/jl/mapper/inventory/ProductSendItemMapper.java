package cn.iocoder.yudao.module.jl.mapper.inventory;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.ProductSendItem;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductSendItemMapper {
    ProductSendItem toEntity(ProductSendItemCreateReqVO dto);

    ProductSendItem toEntity(ProductSendItemUpdateReqVO dto);

    ProductSendItem toEntity(ProductSendItem dto);


    ProductSendItemRespVO toDto(ProductSendItem entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProductSendItem partialUpdate(ProductSendItemUpdateReqVO dto, @MappingTarget ProductSendItem entity);

    List<ProductSendItemExcelVO> toExcelList(List<ProductSendItem> list);

    PageResult<ProductSendItemRespVO> toPage(PageResult<ProductSendItem> page);
}