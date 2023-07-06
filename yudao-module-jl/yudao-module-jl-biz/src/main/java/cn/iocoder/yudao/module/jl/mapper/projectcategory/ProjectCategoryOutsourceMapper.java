package cn.iocoder.yudao.module.jl.mapper.projectcategory;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryOutsource;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectCategoryOutsourceMapper {
    ProjectCategoryOutsource toEntity(ProjectCategoryOutsourceCreateReqVO dto);

    ProjectCategoryOutsource toEntity(ProjectCategoryOutsourceUpdateReqVO dto);

    ProjectCategoryOutsourceRespVO toDto(ProjectCategoryOutsource entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProjectCategoryOutsource partialUpdate(ProjectCategoryOutsourceUpdateReqVO dto, @MappingTarget ProjectCategoryOutsource entity);

    List<ProjectCategoryOutsourceExcelVO> toExcelList(List<ProjectCategoryOutsource> list);

    PageResult<ProjectCategoryOutsourceRespVO> toPage(PageResult<ProjectCategoryOutsource> page);
}