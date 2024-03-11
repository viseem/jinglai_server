package cn.iocoder.yudao.module.jl.mapper.commonoperatelog;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.commonoperatelog.vo.*;
import cn.iocoder.yudao.module.jl.entity.commonoperatelog.CommonOperateLog;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommonOperateLogMapper {
    CommonOperateLog toEntity(CommonOperateLogCreateReqVO dto);

    CommonOperateLog toEntity(CommonOperateLogUpdateReqVO dto);

    CommonOperateLogRespVO toDto(CommonOperateLog entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CommonOperateLog partialUpdate(CommonOperateLogUpdateReqVO dto, @MappingTarget CommonOperateLog entity);

    List<CommonOperateLogExcelVO> toExcelList(List<CommonOperateLog> list);

    PageResult<CommonOperateLogRespVO> toPage(PageResult<CommonOperateLog> page);
}