package cn.iocoder.yudao.module.jl.mapper.project;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.ProjectFund;
import cn.iocoder.yudao.module.jl.entity.projectfundchangelog.ProjectFundChangeLog;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectFundMapper {
    ProjectFund toEntity(ProjectFundCreateReqVO dto);

    ProjectFund toEntity(ProjectFundSaveReqVO dto);
    ProjectFund toEntity(ProjectFundUpdateReqVO dto);

    ProjectFundChangeLog toChangeEntity(ProjectFundUpdateReqVO dto);
    ProjectFundChangeLog entityToChangeEntity(ProjectFund dto);


    ProjectFund toEntity(ProjectFundPaymentUpdateReqVO dto);

    ProjectFundRespVO toDto(ProjectFund entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProjectFund partialUpdate(ProjectFundUpdateReqVO dto, @MappingTarget ProjectFund entity);

    List<ProjectFundExcelVO> toExcelList(List<ProjectFund> list);

    PageResult<ProjectFundRespVO> toPage(PageResult<ProjectFund> page);
}