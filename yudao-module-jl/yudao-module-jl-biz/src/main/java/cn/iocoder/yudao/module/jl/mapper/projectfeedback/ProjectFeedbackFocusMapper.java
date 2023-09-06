package cn.iocoder.yudao.module.jl.mapper.projectfeedback;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.projectfeedback.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectfeedback.ProjectFeedbackFocus;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectFeedbackFocusMapper {
    ProjectFeedbackFocus toEntity(ProjectFeedbackFocusCreateReqVO dto);

    ProjectFeedbackFocus toEntity(ProjectFeedbackFocusUpdateReqVO dto);

    ProjectFeedbackFocusRespVO toDto(ProjectFeedbackFocus entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProjectFeedbackFocus partialUpdate(ProjectFeedbackFocusUpdateReqVO dto, @MappingTarget ProjectFeedbackFocus entity);

    List<ProjectFeedbackFocusExcelVO> toExcelList(List<ProjectFeedbackFocus> list);

    PageResult<ProjectFeedbackFocusRespVO> toPage(PageResult<ProjectFeedbackFocus> page);
}