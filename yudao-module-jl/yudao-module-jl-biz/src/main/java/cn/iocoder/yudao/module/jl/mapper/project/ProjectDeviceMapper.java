package cn.iocoder.yudao.module.jl.mapper.project;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.ProjectDevice;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectDeviceMapper {
    ProjectDevice toEntity(ProjectDeviceCreateReqVO dto);

    ProjectDevice toEntity(ProjectDeviceUpdateReqVO dto);

    ProjectDeviceRespVO toDto(ProjectDevice entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProjectDevice partialUpdate(ProjectDeviceUpdateReqVO dto, @MappingTarget ProjectDevice entity);

    List<ProjectDeviceExcelVO> toExcelList(List<ProjectDevice> list);

    PageResult<ProjectDeviceRespVO> toPage(PageResult<ProjectDevice> page);
}