package cn.iocoder.yudao.module.jl.mapper.inventorystorelog;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.inventorystorelog.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventorystorelog.InventoryStoreLog;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface InventoryStoreLogMapper {
    InventoryStoreLog toEntity(InventoryStoreLogCreateReqVO dto);

    InventoryStoreLog toEntity(InventoryStoreLogUpdateReqVO dto);

    InventoryStoreLogRespVO toDto(InventoryStoreLog entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    InventoryStoreLog partialUpdate(InventoryStoreLogUpdateReqVO dto, @MappingTarget InventoryStoreLog entity);

    List<InventoryStoreLogExcelVO> toExcelList(List<InventoryStoreLog> list);

    PageResult<InventoryStoreLogRespVO> toPage(PageResult<InventoryStoreLog> page);
}