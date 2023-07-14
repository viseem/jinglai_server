package cn.iocoder.yudao.module.jl.mapper.asset;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.asset.vo.*;
import cn.iocoder.yudao.module.jl.entity.asset.AssetDeviceLog;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AssetDeviceLogMapper {
    AssetDeviceLog toEntity(AssetDeviceLogCreateReqVO dto);

    AssetDeviceLog toEntity(AssetDeviceLogUpdateReqVO dto);

    AssetDeviceLogRespVO toDto(AssetDeviceLog entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AssetDeviceLog partialUpdate(AssetDeviceLogUpdateReqVO dto, @MappingTarget AssetDeviceLog entity);

    List<AssetDeviceLogExcelVO> toExcelList(List<AssetDeviceLog> list);

    PageResult<AssetDeviceLogRespVO> toPage(PageResult<AssetDeviceLog> page);
}