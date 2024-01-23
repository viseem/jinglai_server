package cn.iocoder.yudao.module.jl.mapper.commonattachment;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.commonattachment.vo.*;
import cn.iocoder.yudao.module.jl.entity.commonattachment.CommonAttachment;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommonAttachmentMapper {
    CommonAttachment toEntity(CommonAttachmentCreateReqVO dto);

    CommonAttachment toEntity(CommonAttachmentUpdateReqVO dto);

    CommonAttachmentRespVO toDto(CommonAttachment entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CommonAttachment partialUpdate(CommonAttachmentUpdateReqVO dto, @MappingTarget CommonAttachment entity);

    List<CommonAttachmentExcelVO> toExcelList(List<CommonAttachment> list);

    PageResult<CommonAttachmentRespVO> toPage(PageResult<CommonAttachment> page);
}