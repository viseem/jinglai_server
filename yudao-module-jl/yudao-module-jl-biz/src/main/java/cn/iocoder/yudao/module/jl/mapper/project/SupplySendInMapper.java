package cn.iocoder.yudao.module.jl.mapper.project;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.SupplySendIn;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SupplySendInMapper {
    SupplySendIn toEntity(SupplySendInCreateReqVO dto);

    SupplySendIn toEntity(SupplySendInUpdateReqVO dto);

    SupplySendInRespVO toDto(SupplySendIn entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    SupplySendIn partialUpdate(SupplySendInUpdateReqVO dto, @MappingTarget SupplySendIn entity);

    List<SupplySendInExcelVO> toExcelList(List<SupplySendIn> list);

    PageResult<SupplySendInRespVO> toPage(PageResult<SupplySendIn> page);
}