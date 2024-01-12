package cn.iocoder.yudao.module.jl.mapper.commontodo;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.commontodo.vo.*;
import cn.iocoder.yudao.module.jl.entity.commontodo.CommonTodo;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommonTodoMapper {
    CommonTodo toEntity(CommonTodoCreateReqVO dto);

    CommonTodo toEntity(CommonTodoUpdateReqVO dto);

    CommonTodoRespVO toDto(CommonTodo entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CommonTodo partialUpdate(CommonTodoUpdateReqVO dto, @MappingTarget CommonTodo entity);

    List<CommonTodoExcelVO> toExcelList(List<CommonTodo> list);

    PageResult<CommonTodoRespVO> toPage(PageResult<CommonTodo> page);
}