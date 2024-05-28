package cn.iocoder.yudao.module.jl.mapper.commontask;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.commontask.vo.*;
import cn.iocoder.yudao.module.jl.entity.commontask.CommonTask;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommonTaskMapper {
    CommonTask toEntity(CommonTaskCreateReqVO dto);

    CommonTask toEntity(CommonTaskUpdateReqVO dto);

    CommonTaskRespVO toDto(CommonTask entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CommonTask partialUpdate(CommonTaskUpdateReqVO dto, @MappingTarget CommonTask entity);

    List<CommonTaskExcelVO> toExcelList(List<CommonTask> list);

    PageResult<CommonTaskRespVO> toPage(PageResult<CommonTask> page);
}