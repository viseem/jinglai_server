package cn.iocoder.yudao.module.jl.mapper.projectservice;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.projectservice.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectservice.ProjectService;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectServiceMapper {
    ProjectService toEntity(ProjectServiceCreateReqVO dto);

    ProjectService toEntity(ProjectServiceUpdateReqVO dto);

    ProjectServiceRespVO toDto(ProjectService entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProjectService partialUpdate(ProjectServiceUpdateReqVO dto, @MappingTarget ProjectService entity);

    List<ProjectServiceExcelVO> toExcelList(List<ProjectService> list);

    PageResult<ProjectServiceRespVO> toPage(PageResult<ProjectService> page);
}