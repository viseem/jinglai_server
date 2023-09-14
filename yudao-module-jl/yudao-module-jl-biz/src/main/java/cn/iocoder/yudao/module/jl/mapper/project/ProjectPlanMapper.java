package cn.iocoder.yudao.module.jl.mapper.project;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.ProjectPlan;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectPlanMapper {
    ProjectPlan toEntity(ProjectPlanCreateReqVO dto);

    ProjectPlan toEntity(ProjectPlanUpdateReqVO dto);

    ProjectPlanRespVO toDto(ProjectPlan entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProjectPlan partialUpdate(ProjectPlanUpdateReqVO dto, @MappingTarget ProjectPlan entity);

    List<ProjectPlanExcelVO> toExcelList(List<ProjectPlan> list);

    PageResult<ProjectPlanRespVO> toPage(PageResult<ProjectPlan> page);
}