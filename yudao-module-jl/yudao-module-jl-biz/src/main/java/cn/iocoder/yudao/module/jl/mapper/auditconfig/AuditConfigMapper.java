package cn.iocoder.yudao.module.jl.mapper.auditconfig;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.auditconfig.vo.*;
import cn.iocoder.yudao.module.jl.entity.auditconfig.AuditConfig;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuditConfigMapper {
    AuditConfig toEntity(AuditConfigCreateReqVO dto);

    AuditConfig toEntity(AuditConfigUpdateReqVO dto);

    AuditConfigRespVO toDto(AuditConfig entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    AuditConfig partialUpdate(AuditConfigUpdateReqVO dto, @MappingTarget AuditConfig entity);

    List<AuditConfigExcelVO> toExcelList(List<AuditConfig> list);

    PageResult<AuditConfigRespVO> toPage(PageResult<AuditConfig> page);
}