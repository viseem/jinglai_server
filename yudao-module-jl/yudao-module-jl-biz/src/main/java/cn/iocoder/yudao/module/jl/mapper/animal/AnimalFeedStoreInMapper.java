package cn.iocoder.yudao.module.jl.mapper.animal;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.animal.vo.*;
import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedStoreIn;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AnimalFeedStoreInMapper {
    AnimalFeedStoreIn toEntity(AnimalFeedStoreInCreateReqVO dto);

    AnimalFeedStoreIn toEntity(AnimalFeedStoreInUpdateReqVO dto);

    AnimalFeedStoreInRespVO toDto(AnimalFeedStoreIn entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AnimalFeedStoreIn partialUpdate(AnimalFeedStoreInUpdateReqVO dto, @MappingTarget AnimalFeedStoreIn entity);

    List<AnimalFeedStoreInExcelVO> toExcelList(List<AnimalFeedStoreIn> list);

    PageResult<AnimalFeedStoreInRespVO> toPage(PageResult<AnimalFeedStoreIn> page);
}