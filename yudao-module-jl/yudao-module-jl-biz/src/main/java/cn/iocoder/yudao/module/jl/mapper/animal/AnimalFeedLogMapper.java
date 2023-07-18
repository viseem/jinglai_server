package cn.iocoder.yudao.module.jl.mapper.animal;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.animal.vo.*;
import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedLog;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AnimalFeedLogMapper {

    AnimalFeedLog toEntity(AnimalFeedLogSaveReqVO dto);

    AnimalFeedLog toEntity(AnimalFeedLogCreateReqVO dto);

    AnimalFeedLog toEntity(AnimalFeedLogUpdateReqVO dto);

    AnimalFeedLogRespVO toDto(AnimalFeedLog entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AnimalFeedLog partialUpdate(AnimalFeedLogUpdateReqVO dto, @MappingTarget AnimalFeedLog entity);

    List<AnimalFeedLogExcelVO> toExcelList(List<AnimalFeedLog> list);

    PageResult<AnimalFeedLogRespVO> toPage(PageResult<AnimalFeedLog> page);
}