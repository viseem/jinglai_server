package cn.iocoder.yudao.module.jl.service.crm;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.crm.vo.*;
import cn.iocoder.yudao.module.jl.entity.crm.CrmReceipt;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 客户发票 Service 接口
 *
 */
public interface CrmReceiptService {

    /**
     * 创建客户发票
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCrmReceipt(@Valid CrmReceiptCreateReqVO createReqVO);

    /**
     * 更新客户发票
     *
     * @param updateReqVO 更新信息
     */
    void updateCrmReceipt(@Valid CrmReceiptUpdateReqVO updateReqVO);

    /**
     * 删除客户发票
     *
     * @param id 编号
     */
    void deleteCrmReceipt(Long id);

    /**
     * 获得客户发票
     *
     * @param id 编号
     * @return 客户发票
     */
    Optional<CrmReceipt> getCrmReceipt(Long id);

    /**
     * 获得客户发票列表
     *
     * @param ids 编号
     * @return 客户发票列表
     */
    List<CrmReceipt> getCrmReceiptList(Collection<Long> ids);

    /**
     * 获得客户发票分页
     *
     * @param pageReqVO 分页查询
     * @return 客户发票分页
     */
    PageResult<CrmReceipt> getCrmReceiptPage(CrmReceiptPageReqVO pageReqVO, CrmReceiptPageOrder orderV0);

    /**
     * 获得客户发票列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 客户发票列表
     */
    List<CrmReceipt> getCrmReceiptList(CrmReceiptExportReqVO exportReqVO);

}
