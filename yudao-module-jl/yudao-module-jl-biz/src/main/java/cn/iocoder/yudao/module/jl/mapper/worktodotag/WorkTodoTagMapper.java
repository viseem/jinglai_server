package cn.iocoder.yudao.module.jl.mapper.worktodotag;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.worktodotag.vo.*;
import cn.iocoder.yudao.module.jl.entity.worktodotag.WorkTodoTag;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WorkTodoTagMapper {
    WorkTodoTag toEntity(WorkTodoTagCreateReqVO dto);

    WorkTodoTag toEntity(WorkTodoTagUpdateReqVO dto);

    WorkTodoTagRespVO toDto(WorkTodoTag entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    WorkTodoTag partialUpdate(WorkTodoTagUpdateReqVO dto, @MappingTarget WorkTodoTag entity);

    List<WorkTodoTagExcelVO> toExcelList(List<WorkTodoTag> list);

    PageResult<WorkTodoTagRespVO> toPage(PageResult<WorkTodoTag> page);
}