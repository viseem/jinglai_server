package cn.iocoder.yudao.module.jl.mapper.commonlog;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.commonlog.vo.*;
import cn.iocoder.yudao.module.jl.entity.commonlog.CommonLog;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommonLogMapper {
    CommonLog toEntity(CommonLogCreateReqVO dto);

    CommonLog toEntity(CommonLogUpdateReqVO dto);

    CommonLogRespVO toDto(CommonLog entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CommonLog partialUpdate(CommonLogUpdateReqVO dto, @MappingTarget CommonLog entity);

    List<CommonLogExcelVO> toExcelList(List<CommonLog> list);

    PageResult<CommonLogRespVO> toPage(PageResult<CommonLog> page);
}