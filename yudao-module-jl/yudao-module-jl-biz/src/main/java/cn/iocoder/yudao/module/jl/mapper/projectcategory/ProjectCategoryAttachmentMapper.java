package cn.iocoder.yudao.module.jl.mapper.projectcategory;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryAttachment;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectCategoryAttachmentMapper {
    ProjectCategoryAttachment toEntity(ProjectCategoryAttachmentCreateReqVO dto);

    ProjectCategoryAttachment toEntity(ProjectCategoryAttachmentUpdateReqVO dto);

    ProjectCategoryAttachmentRespVO toDto(ProjectCategoryAttachment entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProjectCategoryAttachment partialUpdate(ProjectCategoryAttachmentUpdateReqVO dto, @MappingTarget ProjectCategoryAttachment entity);

    List<ProjectCategoryAttachmentExcelVO> toExcelList(List<ProjectCategoryAttachment> list);

    PageResult<ProjectCategoryAttachmentRespVO> toPage(PageResult<ProjectCategoryAttachment> page);
}