package cn.iocoder.yudao.module.jl.mapper.approval;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.approval.vo.*;
import cn.iocoder.yudao.module.jl.entity.approval.Approval;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ApprovalMapper {
    Approval toEntity(ApprovalCreateReqVO dto);

    Approval toEntity(ApprovalUpdateReqVO dto);

    ApprovalRespVO toDto(Approval entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Approval partialUpdate(ApprovalUpdateReqVO dto, @MappingTarget Approval entity);

    List<ApprovalExcelVO> toExcelList(List<Approval> list);

    PageResult<ApprovalRespVO> toPage(PageResult<Approval> page);
}