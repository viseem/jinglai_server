package cn.iocoder.yudao.module.jl.mapper.project;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.bpm.controller.admin.definition.vo.process.BpmProcessDefinitionRespVO;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.ProjectConstract;
import cn.iocoder.yudao.module.jl.entity.project.ProjectConstractOnly;
import cn.iocoder.yudao.module.jl.entity.user.User;
import org.flowable.common.engine.impl.db.SuspensionState;
import org.flowable.engine.repository.ProcessDefinition;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectConstractMapper {

    ProjectConstract toEntity(ProjectConstractUpdateFieldReqVO dto);

    ProjectConstract toEntity(ProjectConstractCreateReqVO dto);

    ProjectConstract toEntity(ProjectConstractUpdateReqVO dto);

    ProjectConstractRespVO toDto(ProjectConstract entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProjectConstract partialUpdate(ProjectConstractUpdateReqVO dto, @MappingTarget ProjectConstract entity);

    // 把ProjectConstract的sales.nickname映射到ProjectConstractRespVO的salesName

    @Mapping(source = "sales.nickname", target = "salesName")
    ProjectConstractExcelVO toExcel(ProjectConstract projectConstract);

    @IterableMapping(qualifiedByName = "toExcel")
    List<ProjectConstractExcelVO> toExcelList(List<ProjectConstract> list);

    @Named("toExcel")
    default ProjectConstractExcelVO toExcelMapping(ProjectConstract projectConstract) {
        return toExcel(projectConstract);
    }

    PageResult<ProjectConstractRespVO> toPage(PageResult<ProjectConstract> page);

    PageResult<ProjectConstractRespVO> toSimplePage(PageResult<ProjectConstractOnly> page);

    List<ProjectConstract> toEntityList(List<ProjectConstractItemVO> projectConstracts);
}