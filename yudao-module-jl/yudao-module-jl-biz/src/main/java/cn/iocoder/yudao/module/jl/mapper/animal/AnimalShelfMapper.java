package cn.iocoder.yudao.module.jl.mapper.animal;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.animal.vo.*;
import cn.iocoder.yudao.module.jl.entity.animal.AnimalShelf;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AnimalShelfMapper {
    AnimalShelf toEntity(AnimalShelfCreateReqVO dto);

    AnimalShelf toEntity(AnimalShelfUpdateReqVO dto);

    AnimalShelfRespVO toDto(AnimalShelf entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AnimalShelf partialUpdate(AnimalShelfUpdateReqVO dto, @MappingTarget AnimalShelf entity);

    List<AnimalShelfExcelVO> toExcelList(List<AnimalShelf> list);

    PageResult<AnimalShelfRespVO> toPage(PageResult<AnimalShelf> page);
}