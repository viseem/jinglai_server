package cn.iocoder.yudao.module.jl.mapper.animal;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.animal.vo.*;
import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedOrder;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AnimalFeedOrderMapper {
    AnimalFeedOrder toEntity(AnimalFeedOrderCreateReqVO dto);

    AnimalFeedOrder toEntity(AnimalFeedOrderUpdateReqVO dto);

    AnimalFeedOrderRespVO toDto(AnimalFeedOrder entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AnimalFeedOrder partialUpdate(AnimalFeedOrderUpdateReqVO dto, @MappingTarget AnimalFeedOrder entity);

    List<AnimalFeedOrderExcelVO> toExcelList(List<AnimalFeedOrder> list);

    PageResult<AnimalFeedOrderRespVO> toPage(PageResult<AnimalFeedOrder> page);
}