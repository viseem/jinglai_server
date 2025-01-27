package cn.iocoder.yudao.module.jl.mapper.subjectgroup;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.subjectgroup.vo.*;
import cn.iocoder.yudao.module.jl.controller.admin.subjectgroupmember.vo.SubjectGroupMemberRespVO;
import cn.iocoder.yudao.module.jl.entity.subjectgroup.SubjectGroup;
import cn.iocoder.yudao.module.jl.entity.subjectgroupmember.SubjectGroupMember;
import org.mapstruct.*;

import java.util.List;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SubjectGroupMapper {
    SubjectGroup toEntity(SubjectGroupCreateReqVO dto);

    SubjectGroup toEntity(SubjectGroupUpdateReqVO dto);

    SubjectGroupRespVO toDto(SubjectGroup entity);

    PIGroupRespVO toPIDto(SubjectGroup entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SubjectGroup partialUpdate(SubjectGroupUpdateReqVO dto, @MappingTarget SubjectGroup entity);

    List<SubjectGroupExcelVO> toExcelList(List<SubjectGroup> list);

    PageResult<SubjectGroupRespVO> toPage(PageResult<SubjectGroup> page);
}