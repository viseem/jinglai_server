package cn.iocoder.yudao.module.jl.mapper.projectcategory;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategorySupplier;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectCategorySupplierMapper {
    ProjectCategorySupplier toEntity(ProjectCategorySupplierCreateReqVO dto);

    ProjectCategorySupplier toEntity(ProjectCategorySupplierUpdateReqVO dto);

    ProjectCategorySupplierRespVO toDto(ProjectCategorySupplier entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProjectCategorySupplier partialUpdate(ProjectCategorySupplierUpdateReqVO dto, @MappingTarget ProjectCategorySupplier entity);

    List<ProjectCategorySupplierExcelVO> toExcelList(List<ProjectCategorySupplier> list);

    PageResult<ProjectCategorySupplierRespVO> toPage(PageResult<ProjectCategorySupplier> page);
}