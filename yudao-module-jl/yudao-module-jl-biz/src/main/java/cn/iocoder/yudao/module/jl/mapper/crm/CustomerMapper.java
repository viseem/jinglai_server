package cn.iocoder.yudao.module.jl.mapper.crm;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.crm.vo.*;
import cn.iocoder.yudao.module.jl.entity.crm.Customer;
import cn.iocoder.yudao.module.jl.entity.crm.CustomerSimple;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CustomerMapper {
    Customer toEntity(CustomerCreateReqVO dto);
    Customer toEntity(CustomerImportVO dto);

    Customer toEntity(CustomerUpdateReqVO dto);
    CustomerSimple toAppEntity(AppCustomerUpdateReqVO dto);

    CustomerRespVO toDto(Customer entity);

    CustomerRespVO toDto(CustomerSimple entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Customer partialUpdate(CustomerUpdateReqVO dto, @MappingTarget Customer entity);

    List<CustomerExcelVO> toExcelList(List<Customer> list);

    PageResult<CustomerRespVO> toPage(PageResult<Customer> page);

    PageResult<CustomerRespVO> toPageSimple(PageResult<CustomerSimple> page);

}