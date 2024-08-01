package cn.iocoder.yudao.module.jl.mapper.projectoutlog;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.projectoutlog.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectoutlog.ProjectOutLog;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectOutLogMapper {
    ProjectOutLog toEntity(ProjectOutLogCreateReqVO dto);

    ProjectOutLog toEntity(ProjectOutLogUpdateReqVO dto);

    ProjectOutLogRespVO toDto(ProjectOutLog entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProjectOutLog partialUpdate(ProjectOutLogUpdateReqVO dto, @MappingTarget ProjectOutLog entity);

    List<ProjectOutLogExcelVO> toExcelList(List<ProjectOutLog> list);

    PageResult<ProjectOutLogRespVO> toPage(PageResult<ProjectOutLog> page);
}