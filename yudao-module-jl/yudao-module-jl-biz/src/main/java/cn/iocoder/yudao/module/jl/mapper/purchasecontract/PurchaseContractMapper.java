package cn.iocoder.yudao.module.jl.mapper.purchasecontract;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.purchasecontract.vo.*;
import cn.iocoder.yudao.module.jl.entity.purchasecontract.PurchaseContract;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PurchaseContractMapper {
    PurchaseContract toEntity(PurchaseContractCreateReqVO dto);

    PurchaseContract toEntity(PurchaseContractUpdateReqVO dto);

    PurchaseContractRespVO toDto(PurchaseContract entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    PurchaseContract partialUpdate(PurchaseContractUpdateReqVO dto, @MappingTarget PurchaseContract entity);

    List<PurchaseContractExcelVO> toExcelList(List<PurchaseContract> list);

    PageResult<PurchaseContractRespVO> toPage(PageResult<PurchaseContract> page);
}