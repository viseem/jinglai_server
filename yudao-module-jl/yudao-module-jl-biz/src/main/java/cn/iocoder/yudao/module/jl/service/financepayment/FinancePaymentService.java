package cn.iocoder.yudao.module.jl.service.financepayment;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.financepayment.vo.*;
import cn.iocoder.yudao.module.jl.entity.financepayment.FinancePayment;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 财务打款 Service 接口
 *
 */
public interface FinancePaymentService {

    /**
     * 创建财务打款
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createFinancePayment(@Valid FinancePaymentCreateReqVO createReqVO);

    /**
     * 更新财务打款
     *
     * @param updateReqVO 更新信息
     */
    void updateFinancePayment(@Valid FinancePaymentUpdateReqVO updateReqVO);

    /**
     * 删除财务打款
     *
     * @param id 编号
     */
    void deleteFinancePayment(Long id);

    /**
     * 获得财务打款
     *
     * @param id 编号
     * @return 财务打款
     */
    Optional<FinancePayment> getFinancePayment(Long id);

    /**
     * 获得财务打款列表
     *
     * @param ids 编号
     * @return 财务打款列表
     */
    List<FinancePayment> getFinancePaymentList(Collection<Long> ids);

    /**
     * 获得财务打款分页
     *
     * @param pageReqVO 分页查询
     * @return 财务打款分页
     */
    PageResult<FinancePayment> getFinancePaymentPage(FinancePaymentPageReqVO pageReqVO, FinancePaymentPageOrder orderV0);

    /**
     * 获得财务打款列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 财务打款列表
     */
    List<FinancePayment> getFinancePaymentList(FinancePaymentExportReqVO exportReqVO);

}
