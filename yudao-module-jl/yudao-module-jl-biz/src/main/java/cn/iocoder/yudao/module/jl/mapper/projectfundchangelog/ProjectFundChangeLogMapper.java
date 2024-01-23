package cn.iocoder.yudao.module.jl.mapper.projectfundchangelog;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.projectfundchangelog.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectfundchangelog.ProjectFundChangeLog;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectFundChangeLogMapper {
    ProjectFundChangeLog toEntity(ProjectFundChangeLogCreateReqVO dto);

    ProjectFundChangeLog toEntity(ProjectFundChangeLogUpdateReqVO dto);

    ProjectFundChangeLogRespVO toDto(ProjectFundChangeLog entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProjectFundChangeLog partialUpdate(ProjectFundChangeLogUpdateReqVO dto, @MappingTarget ProjectFundChangeLog entity);

    List<ProjectFundChangeLogExcelVO> toExcelList(List<ProjectFundChangeLog> list);

    PageResult<ProjectFundChangeLogRespVO> toPage(PageResult<ProjectFundChangeLog> page);
}