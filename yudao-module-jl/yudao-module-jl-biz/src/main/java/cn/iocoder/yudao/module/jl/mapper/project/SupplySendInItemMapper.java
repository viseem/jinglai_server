package cn.iocoder.yudao.module.jl.mapper.project;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.SupplySendInItem;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SupplySendInItemMapper {
    SupplySendInItem toEntity(SupplySendInItemCreateReqVO dto);

    SupplySendInItem toEntity(SupplySendInItemUpdateReqVO dto);

    SupplySendInItemRespVO toDto(SupplySendInItem entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SupplySendInItem partialUpdate(SupplySendInItemUpdateReqVO dto, @MappingTarget SupplySendInItem entity);

    List<SupplySendInItemExcelVO> toExcelList(List<SupplySendInItem> list);

    PageResult<SupplySendInItemRespVO> toPage(PageResult<SupplySendInItem> page);
}