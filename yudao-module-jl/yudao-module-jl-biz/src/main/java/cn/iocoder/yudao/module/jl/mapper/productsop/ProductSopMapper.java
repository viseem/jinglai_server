package cn.iocoder.yudao.module.jl.mapper.productsop;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.productsop.vo.*;
import cn.iocoder.yudao.module.jl.entity.productsop.ProductSop;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductSopMapper {
    ProductSop toEntity(ProductSopCreateReqVO dto);

    ProductSop toEntity(ProductSopUpdateReqVO dto);

    ProductSopRespVO toDto(ProductSop entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProductSop partialUpdate(ProductSopUpdateReqVO dto, @MappingTarget ProductSop entity);

    List<ProductSopExcelVO> toExcelList(List<ProductSop> list);

    PageResult<ProductSopRespVO> toPage(PageResult<ProductSop> page);
}