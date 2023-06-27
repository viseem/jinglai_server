package cn.iocoder.yudao.module.jl.mapper.projectcategory;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryLog;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectCategoryLogMapper {
    ProjectCategoryLog toEntity(ProjectCategoryLogCreateReqVO dto);

    ProjectCategoryLog toEntity(ProjectCategoryLogUpdateReqVO dto);

    ProjectCategoryLogRespVO toDto(ProjectCategoryLog entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProjectCategoryLog partialUpdate(ProjectCategoryLogUpdateReqVO dto, @MappingTarget ProjectCategoryLog entity);

    List<ProjectCategoryLogExcelVO> toExcelList(List<ProjectCategoryLog> list);

    PageResult<ProjectCategoryLogRespVO> toPage(PageResult<ProjectCategoryLog> page);
}