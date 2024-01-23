package cn.iocoder.yudao.module.jl.mapper.crmsubjectgroup;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.crmsubjectgroup.vo.*;
import cn.iocoder.yudao.module.jl.entity.crmsubjectgroup.CrmSubjectGroup;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CrmSubjectGroupMapper {
    CrmSubjectGroup toEntity(CrmSubjectGroupCreateReqVO dto);

    CrmSubjectGroup toEntity(CrmSubjectGroupUpdateReqVO dto);

    CrmSubjectGroupRespVO toDto(CrmSubjectGroup entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CrmSubjectGroup partialUpdate(CrmSubjectGroupUpdateReqVO dto, @MappingTarget CrmSubjectGroup entity);

    List<CrmSubjectGroupExcelVO> toExcelList(List<CrmSubjectGroup> list);

    PageResult<CrmSubjectGroupRespVO> toPage(PageResult<CrmSubjectGroup> page);
}