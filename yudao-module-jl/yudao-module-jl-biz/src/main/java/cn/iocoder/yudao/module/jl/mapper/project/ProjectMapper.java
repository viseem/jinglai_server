package cn.iocoder.yudao.module.jl.mapper.project;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.Project;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectMapper {
    Project toEntity(ProjectCreateReqVO dto);

    Project toEntity(ProjectUpdateReqVO dto);

    ProjectRespVO toDto(Project entity);
    ProjectCreateReqVO toCreateDto(Project entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Project partialUpdate(ProjectUpdateReqVO dto, @MappingTarget Project entity);

    List<ProjectExcelVO> toExcelList(List<Project> list);

    PageResult<ProjectRespVO> toPage(PageResult<ProjectSimple> page);


    PageResult<ProjectRespVO> toSimplePage(PageResult<ProjectSimple> page);

}