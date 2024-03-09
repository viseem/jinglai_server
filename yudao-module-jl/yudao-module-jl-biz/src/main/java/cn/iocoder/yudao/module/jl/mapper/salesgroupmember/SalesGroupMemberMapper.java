package cn.iocoder.yudao.module.jl.mapper.salesgroupmember;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.salesgroupmember.vo.*;
import cn.iocoder.yudao.module.jl.entity.salesgroupmember.SalesGroupMember;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SalesGroupMemberMapper {
    SalesGroupMember toEntity(SalesGroupMemberCreateReqVO dto);

    SalesGroupMember toEntity(SalesGroupMemberUpdateReqVO dto);

    SalesGroupMemberRespVO toDto(SalesGroupMember entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SalesGroupMember partialUpdate(SalesGroupMemberUpdateReqVO dto, @MappingTarget SalesGroupMember entity);

    List<SalesGroupMemberExcelVO> toExcelList(List<SalesGroupMember> list);

    PageResult<SalesGroupMemberRespVO> toPage(PageResult<SalesGroupMember> page);
}