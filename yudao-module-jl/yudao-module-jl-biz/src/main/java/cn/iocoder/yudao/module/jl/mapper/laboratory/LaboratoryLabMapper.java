package cn.iocoder.yudao.module.jl.mapper.laboratory;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.laboratory.vo.*;
import cn.iocoder.yudao.module.jl.entity.laboratory.LaboratoryLab;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LaboratoryLabMapper {
    LaboratoryLab toEntity(LaboratoryLabCreateReqVO dto);

    LaboratoryLab toEntity(LaboratoryLabUpdateReqVO dto);

    LaboratoryLabRespVO toDto(LaboratoryLab entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    LaboratoryLab partialUpdate(LaboratoryLabUpdateReqVO dto, @MappingTarget LaboratoryLab entity);

    List<LaboratoryLabExcelVO> toExcelList(List<LaboratoryLab> list);

    PageResult<LaboratoryLabRespVO> toPage(PageResult<LaboratoryLab> page);
}