package cn.iocoder.yudao.module.jl.mapper.crm;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.crm.vo.*;
import cn.iocoder.yudao.module.jl.entity.crm.CrmReceiptHead;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CrmReceiptHeadMapper {
    CrmReceiptHead toEntity(CrmReceiptHeadCreateReqVO dto);

    CrmReceiptHead toEntity(CrmReceiptHeadUpdateReqVO dto);

    CrmReceiptHeadRespVO toDto(CrmReceiptHead entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CrmReceiptHead partialUpdate(CrmReceiptHeadUpdateReqVO dto, @MappingTarget CrmReceiptHead entity);

    List<CrmReceiptHeadExcelVO> toExcelList(List<CrmReceiptHead> list);

    PageResult<CrmReceiptHeadRespVO> toPage(PageResult<CrmReceiptHead> page);
}