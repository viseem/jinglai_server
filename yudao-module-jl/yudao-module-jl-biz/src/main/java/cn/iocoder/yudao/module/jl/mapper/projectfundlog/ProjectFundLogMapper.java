package cn.iocoder.yudao.module.jl.mapper.projectfundlog;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.projectfundlog.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectfundlog.ProjectFundLog;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectFundLogMapper {
    ProjectFundLog toEntity(ProjectFundLogCreateReqVO dto);

    List<ProjectFundLog> toEntity(List<ProjectFundLog> dto);

    ProjectFundLog toEntity(ProjectFundLogUpdateReqVO dto);

    ProjectFundLogRespVO toDto(ProjectFundLog entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProjectFundLog partialUpdate(ProjectFundLogUpdateReqVO dto, @MappingTarget ProjectFundLog entity);

    List<ProjectFundLogExcelVO> toExcelList(List<ProjectFundLog> list);

    PageResult<ProjectFundLogRespVO> toPage(PageResult<ProjectFundLog> page);
}