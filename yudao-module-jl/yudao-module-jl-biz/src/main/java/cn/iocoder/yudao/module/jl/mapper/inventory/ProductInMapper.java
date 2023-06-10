package cn.iocoder.yudao.module.jl.mapper.inventory;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.ProductIn;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductInMapper {
    ProductIn toEntity(ProductInCreateReqVO dto);

    ProductIn toEntity(ProductInUpdateReqVO dto);

    ProductInRespVO toDto(ProductIn entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProductIn partialUpdate(ProductInUpdateReqVO dto, @MappingTarget ProductIn entity);

    List<ProductInExcelVO> toExcelList(List<ProductIn> list);

    PageResult<ProductInRespVO> toPage(PageResult<ProductIn> page);
}