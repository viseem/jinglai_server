package cn.iocoder.yudao.module.jl.mapper.picollaboration;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.picollaboration.vo.*;
import cn.iocoder.yudao.module.jl.entity.picollaboration.PiCollaboration;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PiCollaborationMapper {
    PiCollaboration toEntity(PiCollaborationCreateReqVO dto);

    PiCollaboration toEntity(PiCollaborationUpdateReqVO dto);

    PiCollaborationRespVO toDto(PiCollaboration entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PiCollaboration partialUpdate(PiCollaborationUpdateReqVO dto, @MappingTarget PiCollaboration entity);

    List<PiCollaborationExcelVO> toExcelList(List<PiCollaboration> list);

    PageResult<PiCollaborationRespVO> toPage(PageResult<PiCollaboration> page);
}