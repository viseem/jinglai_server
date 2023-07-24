package cn.iocoder.yudao.module.jl.mapper.animal;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.animal.vo.*;
import cn.iocoder.yudao.module.jl.entity.animal.AnimalBox;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AnimalBoxMapper {
    AnimalBox toEntity(AnimalBoxCreateReqVO dto);

    AnimalBox toEntity(AnimalBoxUpdateReqVO dto);

    AnimalBoxRespVO toDto(AnimalBox entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AnimalBox partialUpdate(AnimalBoxUpdateReqVO dto, @MappingTarget AnimalBox entity);

    List<AnimalBoxExcelVO> toExcelList(List<AnimalBox> list);

    PageResult<AnimalBoxRespVO> toPage(PageResult<AnimalBox> page);
}