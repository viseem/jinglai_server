package cn.iocoder.yudao.module.jl.mapper.projectquotation;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.projectquotation.ProjectQuotationUpdatePlanReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.projectquotation.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectquotation.ProjectQuotation;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectQuotationMapper {
    ProjectQuotation toEntity(ProjectQuotationCreateReqVO dto);

    ProjectQuotation toEntity(ProjectQuotationUpdateReqVO dto);

    ProjectQuotation toEntity(ProjectQuotationSaveReqVO dto);
    ProjectQuotation toEntity(ProjectQuotationUpdatePlanReqVO dto);


    ProjectQuotationRespVO toDto(ProjectQuotation entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProjectQuotation partialUpdate(ProjectQuotationUpdateReqVO dto, @MappingTarget ProjectQuotation entity);

    List<ProjectQuotationExcelVO> toExcelList(List<ProjectQuotation> list);

    PageResult<ProjectQuotationRespVO> toPage(PageResult<ProjectQuotation> page);
}