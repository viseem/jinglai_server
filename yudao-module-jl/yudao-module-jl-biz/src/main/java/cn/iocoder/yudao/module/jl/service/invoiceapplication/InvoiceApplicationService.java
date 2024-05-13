package cn.iocoder.yudao.module.jl.service.invoiceapplication;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.invoiceapplication.vo.*;
import cn.iocoder.yudao.module.jl.entity.invoiceapplication.InvoiceApplication;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 开票申请 Service 接口
 *
 */
public interface InvoiceApplicationService {

    /**
     * 创建开票申请
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createInvoiceApplication(@Valid InvoiceApplicationCreateReqVO createReqVO);

    /**
     * 更新开票申请
     *
     * @param updateReqVO 更新信息
     */
    void updateInvoiceApplication(@Valid InvoiceApplicationUpdateReqVO updateReqVO);

    /**
     * 删除开票申请
     *
     * @param id 编号
     */
    void deleteInvoiceApplication(Long id);

    /**
     * 获得开票申请
     *
     * @param id 编号
     * @return 开票申请
     */
    Optional<InvoiceApplication> getInvoiceApplication(Long id);

    /**
     * 获得开票申请列表
     *
     * @param ids 编号
     * @return 开票申请列表
     */
    List<InvoiceApplication> getInvoiceApplicationList(Collection<Long> ids);

    /**
     * 获得开票申请分页
     *
     * @param pageReqVO 分页查询
     * @return 开票申请分页
     */
    PageResult<InvoiceApplication> getInvoiceApplicationPage(InvoiceApplicationPageReqVO pageReqVO, InvoiceApplicationPageOrder orderV0);

    /**
     * 获得开票申请列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 开票申请列表
     */
    List<InvoiceApplication> getInvoiceApplicationList(InvoiceApplicationExportReqVO exportReqVO);

}
