package cn.iocoder.yudao.module.jl.mapper.project;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.ProjectFeedback;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectFeedbackMapper {
    ProjectFeedback toEntity(ProjectFeedbackCreateReqVO dto);

    ProjectFeedback toEntity(ProjectFeedbackUpdateReqVO dto);

    ProjectFeedbackRespVO toDto(ProjectFeedback entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProjectFeedback partialUpdate(ProjectFeedbackUpdateReqVO dto, @MappingTarget ProjectFeedback entity);

    List<ProjectFeedbackExcelVO> toExcelList(List<ProjectFeedback> list);

    PageResult<ProjectFeedbackRespVO> toPage(PageResult<ProjectFeedback> page);
}