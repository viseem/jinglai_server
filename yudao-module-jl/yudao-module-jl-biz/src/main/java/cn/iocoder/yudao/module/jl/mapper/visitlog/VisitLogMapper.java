package cn.iocoder.yudao.module.jl.mapper.visitlog;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.visitlog.vo.*;
import cn.iocoder.yudao.module.jl.entity.visitlog.VisitLog;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface VisitLogMapper {
    VisitLog toEntity(VisitLogCreateReqVO dto);

    VisitLog toEntity(VisitLogUpdateReqVO dto);

    VisitLogRespVO toDto(VisitLog entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    VisitLog partialUpdate(VisitLogUpdateReqVO dto, @MappingTarget VisitLog entity);

    List<VisitLogExcelVO> toExcelList(List<VisitLog> list);

    PageResult<VisitLogRespVO> toPage(PageResult<VisitLog> page);
}