package cn.iocoder.yudao.module.jl.mapper.contract;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.contract.vo.*;
import cn.iocoder.yudao.module.jl.entity.contract.ContractApproval;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ContractApprovalMapper {
    ContractApproval toEntity(ContractApprovalCreateReqVO dto);

    ContractApproval toEntity(ContractApprovalUpdateReqVO dto);

    ContractApprovalRespVO toDto(ContractApproval entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ContractApproval partialUpdate(ContractApprovalUpdateReqVO dto, @MappingTarget ContractApproval entity);

    List<ContractApprovalExcelVO> toExcelList(List<ContractApproval> list);

    PageResult<ContractApprovalRespVO> toPage(PageResult<ContractApproval> page);
}