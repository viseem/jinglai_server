package cn.iocoder.yudao.module.jl.mapper.project;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.Supplier;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SupplierMapper {
    Supplier toEntity(SupplierCreateReqVO dto);

    Supplier toEntity(SupplierUpdateReqVO dto);

    Supplier toEntity(SupplierImportVO dto);


    SupplierRespVO toDto(Supplier entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Supplier partialUpdate(SupplierUpdateReqVO dto, @MappingTarget Supplier entity);

    List<SupplierExcelVO> toExcelList(List<Supplier> list);

    PageResult<SupplierRespVO> toPage(PageResult<Supplier> page);
}