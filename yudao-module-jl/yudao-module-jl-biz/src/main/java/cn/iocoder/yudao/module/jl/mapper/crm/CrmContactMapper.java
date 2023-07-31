package cn.iocoder.yudao.module.jl.mapper.crm;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.crm.vo.*;
import cn.iocoder.yudao.module.jl.entity.crm.CrmContact;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CrmContactMapper {
    CrmContact toEntity(CrmContactCreateReqVO dto);

    CrmContact toEntity(CrmContactUpdateReqVO dto);

    CrmContactRespVO toDto(CrmContact entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    CrmContact partialUpdate(CrmContactUpdateReqVO dto, @MappingTarget CrmContact entity);

    List<CrmContactExcelVO> toExcelList(List<CrmContact> list);

    PageResult<CrmContactRespVO> toPage(PageResult<CrmContact> page);
}