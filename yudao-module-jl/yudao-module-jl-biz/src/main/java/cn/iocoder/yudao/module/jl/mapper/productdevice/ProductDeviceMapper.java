package cn.iocoder.yudao.module.jl.mapper.productdevice;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.productdevice.vo.*;
import cn.iocoder.yudao.module.jl.entity.productdevice.ProductDevice;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductDeviceMapper {
    ProductDevice toEntity(ProductDeviceCreateReqVO dto);

    ProductDevice toEntity(ProductDeviceUpdateReqVO dto);

    ProductDeviceRespVO toDto(ProductDevice entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProductDevice partialUpdate(ProductDeviceUpdateReqVO dto, @MappingTarget ProductDevice entity);

    List<ProductDeviceExcelVO> toExcelList(List<ProductDevice> list);

    PageResult<ProductDeviceRespVO> toPage(PageResult<ProductDevice> page);
}