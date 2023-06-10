package cn.iocoder.yudao.module.jl.service.project;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.ProcurementPayment;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 项目采购单打款 Service 接口
 *
 */
public interface ProcurementPaymentService {

    /**
     * 创建项目采购单打款
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProcurementPayment(@Valid ProcurementPaymentCreateReqVO createReqVO);

    /**
     * 更新项目采购单打款
     *
     * @param updateReqVO 更新信息
     */
    void updateProcurementPayment(@Valid ProcurementPaymentUpdateReqVO updateReqVO);

    /**
     * 删除项目采购单打款
     *
     * @param id 编号
     */
    void deleteProcurementPayment(Long id);

    /**
     * 获得项目采购单打款
     *
     * @param id 编号
     * @return 项目采购单打款
     */
    Optional<ProcurementPayment> getProcurementPayment(Long id);

    /**
     * 获得项目采购单打款列表
     *
     * @param ids 编号
     * @return 项目采购单打款列表
     */
    List<ProcurementPayment> getProcurementPaymentList(Collection<Long> ids);

    /**
     * 获得项目采购单打款分页
     *
     * @param pageReqVO 分页查询
     * @return 项目采购单打款分页
     */
    PageResult<ProcurementPayment> getProcurementPaymentPage(ProcurementPaymentPageReqVO pageReqVO, ProcurementPaymentPageOrder orderV0);

    /**
     * 获得项目采购单打款列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 项目采购单打款列表
     */
    List<ProcurementPayment> getProcurementPaymentList(ProcurementPaymentExportReqVO exportReqVO);

}
