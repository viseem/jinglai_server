package cn.iocoder.yudao.module.jl.mapper.picollaborationitem;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.picollaborationitem.vo.*;
import cn.iocoder.yudao.module.jl.entity.picollaborationitem.PiCollaborationItem;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PiCollaborationItemMapper {
    PiCollaborationItem toEntity(PiCollaborationItemCreateReqVO dto);

    PiCollaborationItem toEntity(PiCollaborationItemUpdateReqVO dto);

    PiCollaborationItemRespVO toDto(PiCollaborationItem entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PiCollaborationItem partialUpdate(PiCollaborationItemUpdateReqVO dto, @MappingTarget PiCollaborationItem entity);

    List<PiCollaborationItemExcelVO> toExcelList(List<PiCollaborationItem> list);

    PageResult<PiCollaborationItemRespVO> toPage(PageResult<PiCollaborationItem> page);
}