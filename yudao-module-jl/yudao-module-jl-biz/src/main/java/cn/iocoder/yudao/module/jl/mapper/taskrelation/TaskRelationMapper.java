package cn.iocoder.yudao.module.jl.mapper.taskrelation;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.taskrelation.vo.*;
import cn.iocoder.yudao.module.jl.entity.taskrelation.TaskRelation;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskRelationMapper {
    TaskRelation toEntity(TaskRelationCreateReqVO dto);

    TaskRelation toEntity(TaskRelationUpdateReqVO dto);

    TaskRelationRespVO toDto(TaskRelation entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TaskRelation partialUpdate(TaskRelationUpdateReqVO dto, @MappingTarget TaskRelation entity);

    List<TaskRelationExcelVO> toExcelList(List<TaskRelation> list);

    PageResult<TaskRelationRespVO> toPage(PageResult<TaskRelation> page);
}