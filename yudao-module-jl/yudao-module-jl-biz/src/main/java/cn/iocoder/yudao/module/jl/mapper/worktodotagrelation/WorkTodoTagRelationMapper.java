package cn.iocoder.yudao.module.jl.mapper.worktodotagrelation;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.worktodotagrelation.vo.*;
import cn.iocoder.yudao.module.jl.entity.worktodotagrelation.WorkTodoTagRelation;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WorkTodoTagRelationMapper {
    WorkTodoTagRelation toEntity(WorkTodoTagRelationCreateReqVO dto);

    WorkTodoTagRelation toEntity(WorkTodoTagRelationUpdateReqVO dto);

    WorkTodoTagRelationRespVO toDto(WorkTodoTagRelation entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    WorkTodoTagRelation partialUpdate(WorkTodoTagRelationUpdateReqVO dto, @MappingTarget WorkTodoTagRelation entity);

    List<WorkTodoTagRelationExcelVO> toExcelList(List<WorkTodoTagRelation> list);

    PageResult<WorkTodoTagRelationRespVO> toPage(PageResult<WorkTodoTagRelation> page);
}