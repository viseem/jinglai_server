package cn.iocoder.yudao.module.jl.service.projectsupplierinvoice;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.projectsupplierinvoice.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectsupplierinvoice.ProjectSupplierInvoice;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 采购供应商发票 Service 接口
 *
 */
public interface ProjectSupplierInvoiceService {

    /**
     * 创建采购供应商发票
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProjectSupplierInvoice(@Valid ProjectSupplierInvoiceCreateReqVO createReqVO);

    /**
     * 更新采购供应商发票
     *
     * @param updateReqVO 更新信息
     */
    void updateProjectSupplierInvoice(@Valid ProjectSupplierInvoiceUpdateReqVO updateReqVO);

    /**
     * 删除采购供应商发票
     *
     * @param id 编号
     */
    void deleteProjectSupplierInvoice(Long id);

    /**
     * 获得采购供应商发票
     *
     * @param id 编号
     * @return 采购供应商发票
     */
    Optional<ProjectSupplierInvoice> getProjectSupplierInvoice(Long id);

    /**
     * 获得采购供应商发票列表
     *
     * @param ids 编号
     * @return 采购供应商发票列表
     */
    List<ProjectSupplierInvoice> getProjectSupplierInvoiceList(Collection<Long> ids);

    /**
     * 获得采购供应商发票分页
     *
     * @param pageReqVO 分页查询
     * @return 采购供应商发票分页
     */
    PageResult<ProjectSupplierInvoice> getProjectSupplierInvoicePage(ProjectSupplierInvoicePageReqVO pageReqVO, ProjectSupplierInvoicePageOrder orderV0);

    /**
     * 获得采购供应商发票列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 采购供应商发票列表
     */
    List<ProjectSupplierInvoice> getProjectSupplierInvoiceList(ProjectSupplierInvoiceExportReqVO exportReqVO);

}
