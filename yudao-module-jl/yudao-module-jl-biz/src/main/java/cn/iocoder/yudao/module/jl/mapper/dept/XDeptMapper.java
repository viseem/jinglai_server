package cn.iocoder.yudao.module.jl.mapper.dept;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.dept.vo.*;
import cn.iocoder.yudao.module.jl.entity.dept.Dept;
import org.mapstruct.*;


@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface XDeptMapper {
    Dept toEntity(DeptCreateReqVO dto);

    Dept toEntity(DeptUpdateReqVO dto);

    DeptRespVO toDto(Dept entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Dept partialUpdate(DeptUpdateReqVO dto, @MappingTarget Dept entity);

    List<DeptExcelVO> toExcelList(List<Dept> list);

    PageResult<DeptRespVO> toPage(PageResult<Dept> page);
}