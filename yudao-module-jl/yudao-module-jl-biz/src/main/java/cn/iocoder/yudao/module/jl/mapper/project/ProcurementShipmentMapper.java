package cn.iocoder.yudao.module.jl.mapper.project;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.ProcurementShipment;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProcurementShipmentMapper {
    ProcurementShipment toEntity(ProcurementShipmentCreateReqVO dto);

    ProcurementShipment toEntity(ProcurementShipmentUpdateReqVO dto);

    ProcurementShipmentRespVO toDto(ProcurementShipment entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProcurementShipment partialUpdate(ProcurementShipmentUpdateReqVO dto, @MappingTarget ProcurementShipment entity);

    List<ProcurementShipmentExcelVO> toExcelList(List<ProcurementShipment> list);

    PageResult<ProcurementShipmentRespVO> toPage(PageResult<ProcurementShipment> page);
}