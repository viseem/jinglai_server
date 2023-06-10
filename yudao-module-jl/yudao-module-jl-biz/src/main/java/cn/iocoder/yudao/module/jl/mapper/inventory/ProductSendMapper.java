package cn.iocoder.yudao.module.jl.mapper.inventory;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.ProductSend;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductSendMapper {
    ProductSend toEntity(ProductSendCreateReqVO dto);

    ProductSend toEntity(ProductSendUpdateReqVO dto);

    ProductSendRespVO toDto(ProductSend entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProductSend partialUpdate(ProductSendUpdateReqVO dto, @MappingTarget ProductSend entity);

    List<ProductSendExcelVO> toExcelList(List<ProductSend> list);

    PageResult<ProductSendRespVO> toPage(PageResult<ProductSend> page);
}