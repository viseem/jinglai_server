package cn.iocoder.yudao.module.jl.mapper.contractfundlog;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.contractfundlog.vo.*;
import cn.iocoder.yudao.module.jl.entity.contractfundlog.ContractFundLog;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ContractFundLogMapper {
    ContractFundLog toEntity(ContractFundLogCreateReqVO dto);

    ContractFundLog toEntity(ContractFundLogUpdateReqVO dto);

    ContractFundLogRespVO toDto(ContractFundLog entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ContractFundLog partialUpdate(ContractFundLogUpdateReqVO dto, @MappingTarget ContractFundLog entity);

    List<ContractFundLogExcelVO> toExcelList(List<ContractFundLog> list);

    PageResult<ContractFundLogRespVO> toPage(PageResult<ContractFundLog> page);
}