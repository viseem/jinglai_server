package cn.iocoder.yudao.module.jl.mapper.projectsupplierinvoice;

import java.util.*;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.projectsupplierinvoice.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectsupplierinvoice.ProjectSupplierInvoice;
import org.mapstruct.*;



@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ProjectSupplierInvoiceMapper {
    ProjectSupplierInvoice toEntity(ProjectSupplierInvoiceCreateReqVO dto);

    ProjectSupplierInvoice toEntity(ProjectSupplierInvoiceUpdateReqVO dto);

    ProjectSupplierInvoiceRespVO toDto(ProjectSupplierInvoice entity);


    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ProjectSupplierInvoice partialUpdate(ProjectSupplierInvoiceUpdateReqVO dto, @MappingTarget ProjectSupplierInvoice entity);

    List<ProjectSupplierInvoiceExcelVO> toExcelList(List<ProjectSupplierInvoice> list);

    PageResult<ProjectSupplierInvoiceRespVO> toPage(PageResult<ProjectSupplierInvoice> page);
}