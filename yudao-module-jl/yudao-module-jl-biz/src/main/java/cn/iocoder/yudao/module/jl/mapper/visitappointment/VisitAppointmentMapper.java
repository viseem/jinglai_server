package cn.iocoder.yudao.module.jl.mapper.visitappointment;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.visitappointment.vo.*;
import cn.iocoder.yudao.module.jl.entity.visitappointment.VisitAppointment;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface VisitAppointmentMapper {
    VisitAppointment toEntity(VisitAppointmentCreateReqVO dto);

    VisitAppointment toEntity(VisitAppointmentUpdateReqVO dto);

    VisitAppointmentRespVO toDto(VisitAppointment entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    VisitAppointment partialUpdate(VisitAppointmentUpdateReqVO dto, @MappingTarget VisitAppointment entity);

    List<VisitAppointmentExcelVO> toExcelList(List<VisitAppointment> list);

    PageResult<VisitAppointmentRespVO> toPage(PageResult<VisitAppointment> page);
}