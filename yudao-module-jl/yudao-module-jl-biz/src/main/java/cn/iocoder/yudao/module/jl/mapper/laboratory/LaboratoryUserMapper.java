package cn.iocoder.yudao.module.jl.mapper.laboratory;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.laboratory.vo.*;
import cn.iocoder.yudao.module.jl.entity.laboratory.LaboratoryUser;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LaboratoryUserMapper {
    LaboratoryUser toEntity(LaboratoryUserCreateReqVO dto);

    LaboratoryUser toEntity(LaboratoryUserUpdateReqVO dto);

    LaboratoryUserRespVO toDto(LaboratoryUser entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    LaboratoryUser partialUpdate(LaboratoryUserUpdateReqVO dto, @MappingTarget LaboratoryUser entity);

    List<LaboratoryUserExcelVO> toExcelList(List<LaboratoryUser> list);

    PageResult<LaboratoryUserRespVO> toPage(PageResult<LaboratoryUser> page);
}