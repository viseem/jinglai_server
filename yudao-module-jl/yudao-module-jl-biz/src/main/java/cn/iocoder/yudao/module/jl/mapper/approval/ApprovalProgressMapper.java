package cn.iocoder.yudao.module.jl.mapper.approval;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.approval.vo.*;
import cn.iocoder.yudao.module.jl.entity.approval.ApprovalProgress;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApprovalProgressMapper {
    ApprovalProgress toEntity(ApprovalProgressCreateReqVO dto);

    ApprovalProgress toEntity(ApprovalProgressUpdateReqVO dto);

    ApprovalProgressRespVO toDto(ApprovalProgress entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ApprovalProgress partialUpdate(ApprovalProgressUpdateReqVO dto, @MappingTarget ApprovalProgress entity);

    List<ApprovalProgressExcelVO> toExcelList(List<ApprovalProgress> list);

    PageResult<ApprovalProgressRespVO> toPage(PageResult<ApprovalProgress> page);
}