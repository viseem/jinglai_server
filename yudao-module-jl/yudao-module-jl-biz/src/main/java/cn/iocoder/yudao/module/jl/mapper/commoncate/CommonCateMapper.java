package cn.iocoder.yudao.module.jl.mapper.commoncate;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.commoncate.vo.*;
import cn.iocoder.yudao.module.jl.entity.commoncate.CommonCate;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommonCateMapper {
    CommonCate toEntity(CommonCateCreateReqVO dto);

    CommonCate toEntity(CommonCateUpdateReqVO dto);

    CommonCateRespVO toDto(CommonCate entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CommonCate partialUpdate(CommonCateUpdateReqVO dto, @MappingTarget CommonCate entity);

    List<CommonCateExcelVO> toExcelList(List<CommonCate> list);

    PageResult<CommonCateRespVO> toPage(PageResult<CommonCate> page);
}