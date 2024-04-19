package cn.iocoder.yudao.module.jl.mapper.product;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.product.vo.*;
import cn.iocoder.yudao.module.jl.entity.product.Product;
import cn.iocoder.yudao.module.jl.entity.product.ProductDetail;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductMapper {
    Product toEntity(ProductCreateReqVO dto);

    Product toEntity(ProductUpdateReqVO dto);
    ProductDetail toEntityDetail(ProductUpdateReqVO dto);

    ProductRespVO toDto(Product entity);

    ProductRespVO toDto(ProductDetail entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Product partialUpdate(ProductUpdateReqVO dto, @MappingTarget Product entity);

    List<ProductExcelVO> toExcelList(List<Product> list);

    PageResult<ProductRespVO> toPage(PageResult<Product> page);
}