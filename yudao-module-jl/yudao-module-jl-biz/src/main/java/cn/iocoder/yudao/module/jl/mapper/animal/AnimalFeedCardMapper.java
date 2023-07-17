package cn.iocoder.yudao.module.jl.mapper.animal;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.animal.vo.*;
import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedCard;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AnimalFeedCardMapper {
    AnimalFeedCard toEntity(AnimalFeedCardCreateReqVO dto);

    AnimalFeedCard toEntity(AnimalFeedCardUpdateReqVO dto);

    AnimalFeedCardRespVO toDto(AnimalFeedCard entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AnimalFeedCard partialUpdate(AnimalFeedCardUpdateReqVO dto, @MappingTarget AnimalFeedCard entity);

    List<AnimalFeedCardExcelVO> toExcelList(List<AnimalFeedCard> list);

    PageResult<AnimalFeedCardRespVO> toPage(PageResult<AnimalFeedCard> page);
}