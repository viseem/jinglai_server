package cn.iocoder.yudao.module.jl.mapper.project;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.SupplyLogAttachment;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SupplyLogAttachmentMapper {
    SupplyLogAttachment toEntity(SupplyLogAttachmentCreateReqVO dto);

    SupplyLogAttachment toEntity(SupplyLogAttachmentUpdateReqVO dto);

    SupplyLogAttachmentRespVO toDto(SupplyLogAttachment entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SupplyLogAttachment partialUpdate(SupplyLogAttachmentUpdateReqVO dto, @MappingTarget SupplyLogAttachment entity);

    List<SupplyLogAttachmentExcelVO> toExcelList(List<SupplyLogAttachment> list);

    PageResult<SupplyLogAttachmentRespVO> toPage(PageResult<SupplyLogAttachment> page);
}