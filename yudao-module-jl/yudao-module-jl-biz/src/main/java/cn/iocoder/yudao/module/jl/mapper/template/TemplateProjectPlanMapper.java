package cn.iocoder.yudao.module.jl.mapper.template;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.template.vo.*;
import cn.iocoder.yudao.module.jl.entity.template.TemplateProjectPlan;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TemplateProjectPlanMapper {
    TemplateProjectPlan toEntity(TemplateProjectPlanCreateReqVO dto);

    TemplateProjectPlan toEntity(TemplateProjectPlanUpdateReqVO dto);

    TemplateProjectPlanRespVO toDto(TemplateProjectPlan entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TemplateProjectPlan partialUpdate(TemplateProjectPlanUpdateReqVO dto, @MappingTarget TemplateProjectPlan entity);

    List<TemplateProjectPlanExcelVO> toExcelList(List<TemplateProjectPlan> list);

    PageResult<TemplateProjectPlanRespVO> toPage(PageResult<TemplateProjectPlan> page);
}