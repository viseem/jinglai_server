package cn.iocoder.yudao.module.jl.mapper.worktodo;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.worktodo.vo.*;
import cn.iocoder.yudao.module.jl.entity.worktodo.WorkTodo;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface WorkTodoMapper {
    WorkTodo toEntity(WorkTodoCreateReqVO dto);

    WorkTodo toEntity(WorkTodoUpdateReqVO dto);

    WorkTodoRespVO toDto(WorkTodo entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    WorkTodo partialUpdate(WorkTodoUpdateReqVO dto, @MappingTarget WorkTodo entity);

    List<WorkTodoExcelVO> toExcelList(List<WorkTodo> list);

    PageResult<WorkTodoRespVO> toPage(PageResult<WorkTodo> page);
}