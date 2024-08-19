package cn.iocoder.yudao.module.jl.mapper.devicecate;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.devicecate.vo.*;
import cn.iocoder.yudao.module.jl.entity.devicecate.DeviceCate;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface DeviceCateMapper {
    DeviceCate toEntity(DeviceCateCreateReqVO dto);

    DeviceCate toEntity(DeviceCateUpdateReqVO dto);

    DeviceCateRespVO toDto(DeviceCate entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    DeviceCate partialUpdate(DeviceCateUpdateReqVO dto, @MappingTarget DeviceCate entity);

    List<DeviceCateExcelVO> toExcelList(List<DeviceCate> list);

    PageResult<DeviceCateRespVO> toPage(PageResult<DeviceCate> page);
}