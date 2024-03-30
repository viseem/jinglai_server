package cn.iocoder.yudao.module.jl.mapper.productuser;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.productuser.vo.*;
import cn.iocoder.yudao.module.jl.entity.productuser.ProductUser;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProductUserMapper {
    ProductUser toEntity(ProductUserCreateReqVO dto);

    ProductUser toEntity(ProductUserUpdateReqVO dto);

    ProductUserRespVO toDto(ProductUser entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProductUser partialUpdate(ProductUserUpdateReqVO dto, @MappingTarget ProductUser entity);

    List<ProductUserExcelVO> toExcelList(List<ProductUser> list);

    PageResult<ProductUserRespVO> toPage(PageResult<ProductUser> page);
}