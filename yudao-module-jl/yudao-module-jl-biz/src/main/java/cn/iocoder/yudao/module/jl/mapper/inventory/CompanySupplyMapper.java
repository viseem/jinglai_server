package cn.iocoder.yudao.module.jl.mapper.inventory;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.CompanySupply;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompanySupplyMapper {
    CompanySupply toEntity(CompanySupplyCreateReqVO dto);

    CompanySupply toEntity(CompanySupplyUpdateReqVO dto);

    CompanySupplyRespVO toDto(CompanySupply entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CompanySupply partialUpdate(CompanySupplyUpdateReqVO dto, @MappingTarget CompanySupply entity);

    List<CompanySupplyExcelVO> toExcelList(List<CompanySupply> list);

    PageResult<CompanySupplyRespVO> toPage(PageResult<CompanySupply> page);
}