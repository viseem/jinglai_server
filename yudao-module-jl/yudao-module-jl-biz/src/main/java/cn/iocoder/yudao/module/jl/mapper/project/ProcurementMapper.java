package cn.iocoder.yudao.module.jl.mapper.project;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.Procurement;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProcurementMapper {
    Procurement toEntity(ProcurementCreateReqVO dto);

    Procurement toEntity(ProcurementUpdateReqVO dto);

    Procurement toEntity(ProcurementSaveReqVO dto);

    ProcurementRespVO toDto(Procurement entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Procurement partialUpdate(ProcurementUpdateReqVO dto, @MappingTarget Procurement entity);

    List<ProcurementExcelVO> toExcelList(List<Procurement> list);

    PageResult<ProcurementRespVO> toPage(PageResult<Procurement> page);
}