package cn.iocoder.yudao.module.jl.mapper.projectsettlement;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.projectquotation.vo.ProjectQuotationExcelVO;
import cn.iocoder.yudao.module.jl.controller.admin.projectquotation.vo.ProjectQuotationItemVO;
import cn.iocoder.yudao.module.jl.controller.admin.projectsettlement.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectsettlement.ProjectSettlement;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectSettlementMapper {
    ProjectSettlement toEntity(ProjectSettlementCreateReqVO dto);

    ProjectSettlement toEntity(ProjectSettlementUpdateReqVO dto);

    ProjectSettlementRespVO toDto(ProjectSettlement entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProjectSettlement partialUpdate(ProjectSettlementUpdateReqVO dto, @MappingTarget ProjectSettlement entity);

    List<ProjectSettlementExcelVO> toExcelList(List<ProjectSettlement> list);
    List<ProjectSettlementExcelVO> toExcelList2(List<ProjectQuotationItemVO> list);


    PageResult<ProjectSettlementRespVO> toPage(PageResult<ProjectSettlement> page);
}