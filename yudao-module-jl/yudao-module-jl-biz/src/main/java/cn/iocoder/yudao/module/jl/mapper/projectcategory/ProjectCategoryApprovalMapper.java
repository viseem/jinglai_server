package cn.iocoder.yudao.module.jl.mapper.projectcategory;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryApproval;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectCategoryApprovalMapper {

    ProjectCategoryApproval toEntity(ProjectCategoryApprovalSaveReqVO dto);
    ProjectCategoryApproval toEntity(ProjectCategoryApprovalCreateReqVO dto);

    ProjectCategoryApproval toEntity(ProjectCategoryApprovalUpdateReqVO dto);

    ProjectCategoryApprovalRespVO toDto(ProjectCategoryApproval entity);
    ProjectCategoryApprovalDetailRespVO toDetailDto(ProjectCategoryApproval entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProjectCategoryApproval partialUpdate(ProjectCategoryApprovalUpdateReqVO dto, @MappingTarget ProjectCategoryApproval entity);

    List<ProjectCategoryApprovalExcelVO> toExcelList(List<ProjectCategoryApproval> list);

    PageResult<ProjectCategoryApprovalRespVO> toPage(PageResult<ProjectCategoryApproval> page);
}