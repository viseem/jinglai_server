package cn.iocoder.yudao.module.jl.mapper.cellbase;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.cellbase.vo.*;
import cn.iocoder.yudao.module.jl.entity.cellbase.CellBase;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CellBaseMapper {
    CellBase toEntity(CellBaseCreateReqVO dto);

    CellBase toEntity(CellBaseUpdateReqVO dto);

    CellBaseRespVO toDto(CellBase entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CellBase partialUpdate(CellBaseUpdateReqVO dto, @MappingTarget CellBase entity);

    List<CellBaseExcelVO> toExcelList(List<CellBase> list);

    PageResult<CellBaseRespVO> toPage(PageResult<CellBase> page);
}