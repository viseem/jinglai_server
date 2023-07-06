package cn.iocoder.yudao.module.jl.mapper.project;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.ProjectApproval;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectApprovalMapper {
    ProjectApproval toEntity(ProjectApprovalCreateReqVO dto);

    ProjectApproval toEntity(ProjectApprovalUpdateReqVO dto);

    ProjectApprovalRespVO toDto(ProjectApproval entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProjectApproval partialUpdate(ProjectApprovalUpdateReqVO dto, @MappingTarget ProjectApproval entity);

    List<ProjectApprovalExcelVO> toExcelList(List<ProjectApproval> list);

    PageResult<ProjectApprovalRespVO> toPage(PageResult<ProjectApproval> page);
}