package cn.iocoder.yudao.module.jl.mapper.subjectgroupmember;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.subjectgroupmember.vo.*;
import cn.iocoder.yudao.module.jl.entity.subjectgroupmember.SubjectGroupMember;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubjectGroupMemberMapper {
    SubjectGroupMember toEntity(SubjectGroupMemberCreateReqVO dto);

    SubjectGroupMember toEntity(SubjectGroupMemberUpdateReqVO dto);

    SubjectGroupMemberRespVO toDto(SubjectGroupMember entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SubjectGroupMember partialUpdate(SubjectGroupMemberUpdateReqVO dto, @MappingTarget SubjectGroupMember entity);

    List<SubjectGroupMemberExcelVO> toExcelList(List<SubjectGroupMember> list);

    PageResult<SubjectGroupMemberRespVO> toPage(PageResult<SubjectGroupMember> page);
}