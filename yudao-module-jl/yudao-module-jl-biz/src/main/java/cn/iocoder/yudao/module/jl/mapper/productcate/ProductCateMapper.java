package cn.iocoder.yudao.module.jl.mapper.productcate;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.productcate.vo.*;
import cn.iocoder.yudao.module.jl.entity.productcate.ProductCate;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductCateMapper {
    ProductCate toEntity(ProductCateCreateReqVO dto);

    ProductCate toEntity(ProductCateUpdateReqVO dto);

    ProductCateRespVO toDto(ProductCate entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProductCate partialUpdate(ProductCateUpdateReqVO dto, @MappingTarget ProductCate entity);

    List<ProductCateExcelVO> toExcelList(List<ProductCate> list);

    PageResult<ProductCateRespVO> toPage(PageResult<ProductCate> page);
}