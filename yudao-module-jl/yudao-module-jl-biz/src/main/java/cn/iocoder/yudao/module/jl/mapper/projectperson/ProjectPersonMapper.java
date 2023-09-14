package cn.iocoder.yudao.module.jl.mapper.projectperson;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.projectperson.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectperson.ProjectPerson;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectPersonMapper {
    ProjectPerson toEntity(ProjectPersonCreateReqVO dto);

    ProjectPerson toEntity(ProjectPersonUpdateReqVO dto);

    ProjectPersonRespVO toDto(ProjectPerson entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProjectPerson partialUpdate(ProjectPersonUpdateReqVO dto, @MappingTarget ProjectPerson entity);

    List<ProjectPersonExcelVO> toExcelList(List<ProjectPerson> list);

    PageResult<ProjectPersonRespVO> toPage(PageResult<ProjectPerson> page);
}