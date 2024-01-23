package cn.iocoder.yudao.module.jl.mapper.reminder;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.reminder.vo.*;
import cn.iocoder.yudao.module.jl.entity.reminder.Reminder;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ReminderMapper {
    Reminder toEntity(ReminderCreateReqVO dto);

    Reminder toEntity(ReminderUpdateReqVO dto);

    ReminderRespVO toDto(Reminder entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Reminder partialUpdate(ReminderUpdateReqVO dto, @MappingTarget Reminder entity);

    List<ReminderExcelVO> toExcelList(List<Reminder> list);

    PageResult<ReminderRespVO> toPage(PageResult<Reminder> page);
}