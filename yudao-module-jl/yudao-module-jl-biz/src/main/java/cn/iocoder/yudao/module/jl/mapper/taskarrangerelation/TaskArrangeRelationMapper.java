package cn.iocoder.yudao.module.jl.mapper.taskarrangerelation;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.taskarrangerelation.vo.*;
import cn.iocoder.yudao.module.jl.entity.taskarrangerelation.TaskArrangeRelation;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskArrangeRelationMapper {
    TaskArrangeRelation toEntity(TaskArrangeRelationCreateReqVO dto);

    TaskArrangeRelation toEntity(TaskArrangeRelationUpdateReqVO dto);

    TaskArrangeRelationRespVO toDto(TaskArrangeRelation entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TaskArrangeRelation partialUpdate(TaskArrangeRelationUpdateReqVO dto, @MappingTarget TaskArrangeRelation entity);

    List<TaskArrangeRelationExcelVO> toExcelList(List<TaskArrangeRelation> list);

    PageResult<TaskArrangeRelationRespVO> toPage(PageResult<TaskArrangeRelation> page);
}