package cn.iocoder.yudao.module.jl.mapper.crm;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.crm.vo.*;
import cn.iocoder.yudao.module.jl.entity.crm.CrmReceipt;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CrmReceiptMapper {
    CrmReceipt toEntity(CrmReceiptCreateReqVO dto);

    CrmReceipt toEntity(CrmReceiptUpdateReqVO dto);

    CrmReceiptRespVO toDto(CrmReceipt entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CrmReceipt partialUpdate(CrmReceiptUpdateReqVO dto, @MappingTarget CrmReceipt entity);

    List<CrmReceiptExcelVO> toExcelList(List<CrmReceipt> list);

    PageResult<CrmReceiptRespVO> toPage(PageResult<CrmReceipt> page);
}