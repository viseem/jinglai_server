package cn.iocoder.yudao.module.jl.mapper.taskproduct;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.taskproduct.vo.*;
import cn.iocoder.yudao.module.jl.entity.taskproduct.TaskProduct;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskProductMapper {
    TaskProduct toEntity(TaskProductCreateReqVO dto);

    TaskProduct toEntity(TaskProductUpdateReqVO dto);

    TaskProductRespVO toDto(TaskProduct entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    TaskProduct partialUpdate(TaskProductUpdateReqVO dto, @MappingTarget TaskProduct entity);

    List<TaskProductExcelVO> toExcelList(List<TaskProduct> list);

    PageResult<TaskProductRespVO> toPage(PageResult<TaskProduct> page);
}