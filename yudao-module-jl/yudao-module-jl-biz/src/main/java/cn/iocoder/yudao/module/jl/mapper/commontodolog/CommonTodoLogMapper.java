package cn.iocoder.yudao.module.jl.mapper.commontodolog;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.commontodolog.vo.*;
import cn.iocoder.yudao.module.jl.entity.commontodolog.CommonTodoLog;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommonTodoLogMapper {
    CommonTodoLog toEntity(CommonTodoLogCreateReqVO dto);

    CommonTodoLog toEntity(CommonTodoLogUpdateReqVO dto);

    CommonTodoLog toEntity(CommonTodoLogSaveStatusReqVO dto);


    CommonTodoLogRespVO toDto(CommonTodoLog entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CommonTodoLog partialUpdate(CommonTodoLogUpdateReqVO dto, @MappingTarget CommonTodoLog entity);

    List<CommonTodoLogExcelVO> toExcelList(List<CommonTodoLog> list);

    PageResult<CommonTodoLogRespVO> toPage(PageResult<CommonTodoLog> page);
}