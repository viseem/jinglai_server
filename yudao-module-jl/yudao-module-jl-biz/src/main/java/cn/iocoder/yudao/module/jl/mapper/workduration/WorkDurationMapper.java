package cn.iocoder.yudao.module.jl.mapper.workduration;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.workduration.vo.*;
import cn.iocoder.yudao.module.jl.entity.workduration.WorkDuration;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WorkDurationMapper {
    WorkDuration toEntity(WorkDurationCreateReqVO dto);

    WorkDuration toEntity(WorkDurationUpdateReqVO dto);

    WorkDurationRespVO toDto(WorkDuration entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    WorkDuration partialUpdate(WorkDurationUpdateReqVO dto, @MappingTarget WorkDuration entity);

    List<WorkDurationExcelVO> toExcelList(List<WorkDuration> list);

    PageResult<WorkDurationRespVO> toPage(PageResult<WorkDuration> page);
}