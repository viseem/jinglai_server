package cn.iocoder.yudao.module.jl.mapper.project;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.ProcurementItem;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProcurementItemMapper {
    ProcurementItem toEntity(ProcurementItemCreateReqVO dto);

    ProcurementItem toEntity(ProcurementItemUpdateReqVO dto);

    ProcurementItemRespVO toDto(ProcurementItem entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProcurementItem partialUpdate(ProcurementItemUpdateReqVO dto, @MappingTarget ProcurementItem entity);

    List<ProcurementItemExcelVO> toExcelList(List<ProcurementItem> list);

    PageResult<ProcurementItemRespVO> toPage(PageResult<ProcurementItem> page);
}