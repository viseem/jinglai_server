package cn.iocoder.yudao.module.jl.mapper.projectpushlog;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.projectpushlog.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectpushlog.ProjectPushLog;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectPushLogMapper {
    ProjectPushLog toEntity(ProjectPushLogCreateReqVO dto);

    ProjectPushLog toEntity(ProjectPushLogUpdateReqVO dto);

    ProjectPushLogRespVO toDto(ProjectPushLog entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProjectPushLog partialUpdate(ProjectPushLogUpdateReqVO dto, @MappingTarget ProjectPushLog entity);

    List<ProjectPushLogExcelVO> toExcelList(List<ProjectPushLog> list);

    PageResult<ProjectPushLogRespVO> toPage(PageResult<ProjectPushLog> page);
}