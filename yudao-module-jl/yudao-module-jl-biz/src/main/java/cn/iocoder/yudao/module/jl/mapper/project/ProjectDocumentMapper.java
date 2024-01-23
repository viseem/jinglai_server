package cn.iocoder.yudao.module.jl.mapper.project;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.ProjectDocument;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectDocumentMapper {
    ProjectDocument toEntity(ProjectDocumentCreateReqVO dto);

    ProjectDocument toEntity(ProjectDocumentUpdateReqVO dto);

    ProjectDocumentRespVO toDto(ProjectDocument entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProjectDocument partialUpdate(ProjectDocumentUpdateReqVO dto, @MappingTarget ProjectDocument entity);

    List<ProjectDocumentExcelVO> toExcelList(List<ProjectDocument> list);

    PageResult<ProjectDocumentRespVO> toPage(PageResult<ProjectDocument> page);
}