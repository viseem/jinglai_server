package cn.iocoder.yudao.module.jl.service.quotationchangelog;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.quotationchangelog.vo.*;
import cn.iocoder.yudao.module.jl.entity.quotationchangelog.QuotationChangeLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 报价变更日志 Service 接口
 *
 */
public interface QuotationChangeLogService {

    /**
     * 创建报价变更日志
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createQuotationChangeLog(@Valid QuotationChangeLogCreateReqVO createReqVO);

    /**
     * 更新报价变更日志
     *
     * @param updateReqVO 更新信息
     */
    void updateQuotationChangeLog(@Valid QuotationChangeLogUpdateReqVO updateReqVO);

    /**
     * 删除报价变更日志
     *
     * @param id 编号
     */
    void deleteQuotationChangeLog(Long id);

    /**
     * 获得报价变更日志
     *
     * @param id 编号
     * @return 报价变更日志
     */
    Optional<QuotationChangeLog> getQuotationChangeLog(Long id);

    /**
     * 获得报价变更日志列表
     *
     * @param ids 编号
     * @return 报价变更日志列表
     */
    List<QuotationChangeLog> getQuotationChangeLogList(Collection<Long> ids);

    /**
     * 获得报价变更日志分页
     *
     * @param pageReqVO 分页查询
     * @return 报价变更日志分页
     */
    PageResult<QuotationChangeLog> getQuotationChangeLogPage(QuotationChangeLogPageReqVO pageReqVO, QuotationChangeLogPageOrder orderV0);

    /**
     * 获得报价变更日志列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 报价变更日志列表
     */
    List<QuotationChangeLog> getQuotationChangeLogList(QuotationChangeLogExportReqVO exportReqVO);

}
