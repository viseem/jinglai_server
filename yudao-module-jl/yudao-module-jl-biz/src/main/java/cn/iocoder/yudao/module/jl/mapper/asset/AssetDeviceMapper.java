package cn.iocoder.yudao.module.jl.mapper.asset;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.asset.vo.*;
import cn.iocoder.yudao.module.jl.entity.asset.AssetDevice;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AssetDeviceMapper {
    AssetDevice toEntity(AssetDeviceCreateReqVO dto);

    AssetDevice toEntity(AssetDeviceUpdateReqVO dto);

    AssetDeviceRespVO toDto(AssetDevice entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AssetDevice partialUpdate(AssetDeviceUpdateReqVO dto, @MappingTarget AssetDevice entity);

    List<AssetDeviceExcelVO> toExcelList(List<AssetDevice> list);

    PageResult<AssetDeviceRespVO> toPage(PageResult<AssetDevice> page);
}