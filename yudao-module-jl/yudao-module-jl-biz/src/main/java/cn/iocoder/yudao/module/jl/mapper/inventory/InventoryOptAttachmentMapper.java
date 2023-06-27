package cn.iocoder.yudao.module.jl.mapper.inventory;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryOptAttachment;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface InventoryOptAttachmentMapper {
    InventoryOptAttachment toEntity(InventoryOptAttachmentCreateReqVO dto);

    InventoryOptAttachment toEntity(InventoryOptAttachmentUpdateReqVO dto);

    InventoryOptAttachmentRespVO toDto(InventoryOptAttachment entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    InventoryOptAttachment partialUpdate(InventoryOptAttachmentUpdateReqVO dto, @MappingTarget InventoryOptAttachment entity);

    List<InventoryOptAttachmentExcelVO> toExcelList(List<InventoryOptAttachment> list);

    PageResult<InventoryOptAttachmentRespVO> toPage(PageResult<InventoryOptAttachment> page);
}