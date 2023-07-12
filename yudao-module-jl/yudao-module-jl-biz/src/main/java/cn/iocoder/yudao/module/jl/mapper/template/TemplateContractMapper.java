package cn.iocoder.yudao.module.jl.mapper.template;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.template.vo.*;
import cn.iocoder.yudao.module.jl.entity.template.TemplateContract;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TemplateContractMapper {
    TemplateContract toEntity(TemplateContractCreateReqVO dto);

    TemplateContract toEntity(TemplateContractUpdateReqVO dto);

    TemplateContractRespVO toDto(TemplateContract entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TemplateContract partialUpdate(TemplateContractUpdateReqVO dto, @MappingTarget TemplateContract entity);

    List<TemplateContractExcelVO> toExcelList(List<TemplateContract> list);

    PageResult<TemplateContractRespVO> toPage(PageResult<TemplateContract> page);
}