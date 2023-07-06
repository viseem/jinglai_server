package cn.iocoder.yudao.module.jl.mapper.project;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.ProjectReimburse;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectReimburseMapper {
    ProjectReimburse toEntity(ProjectReimburseCreateReqVO dto);

    ProjectReimburse toEntity(ProjectReimburseUpdateReqVO dto);

    ProjectReimburseRespVO toDto(ProjectReimburse entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProjectReimburse partialUpdate(ProjectReimburseUpdateReqVO dto, @MappingTarget ProjectReimburse entity);

    List<ProjectReimburseExcelVO> toExcelList(List<ProjectReimburse> list);

    PageResult<ProjectReimburseRespVO> toPage(PageResult<ProjectReimburse> page);
}