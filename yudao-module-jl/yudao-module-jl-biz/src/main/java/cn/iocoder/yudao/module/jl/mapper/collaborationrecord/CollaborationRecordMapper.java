package cn.iocoder.yudao.module.jl.mapper.collaborationrecord;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.collaborationrecord.vo.*;
import cn.iocoder.yudao.module.jl.entity.collaborationrecord.CollaborationRecord;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CollaborationRecordMapper {
    CollaborationRecord toEntity(CollaborationRecordCreateReqVO dto);

    CollaborationRecord toEntity(CollaborationRecordUpdateReqVO dto);

    CollaborationRecordRespVO toDto(CollaborationRecord entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CollaborationRecord partialUpdate(CollaborationRecordUpdateReqVO dto, @MappingTarget CollaborationRecord entity);

    List<CollaborationRecordExcelVO> toExcelList(List<CollaborationRecord> list);

    PageResult<CollaborationRecordRespVO> toPage(PageResult<CollaborationRecord> page);
}