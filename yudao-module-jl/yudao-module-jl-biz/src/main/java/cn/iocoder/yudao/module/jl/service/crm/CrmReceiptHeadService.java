package cn.iocoder.yudao.module.jl.service.crm;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.crm.vo.*;
import cn.iocoder.yudao.module.jl.entity.crm.CrmReceiptHead;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 客户发票抬头 Service 接口
 *
 */
public interface CrmReceiptHeadService {

    /**
     * 创建客户发票抬头
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCrmReceiptHead(@Valid CrmReceiptHeadCreateReqVO createReqVO);

    /**
     * 更新客户发票抬头
     *
     * @param updateReqVO 更新信息
     */
    void updateCrmReceiptHead(@Valid CrmReceiptHeadUpdateReqVO updateReqVO);

    /**
     * 删除客户发票抬头
     *
     * @param id 编号
     */
    void deleteCrmReceiptHead(Long id);

    /**
     * 获得客户发票抬头
     *
     * @param id 编号
     * @return 客户发票抬头
     */
    Optional<CrmReceiptHead> getCrmReceiptHead(Long id);

    /**
     * 获得客户发票抬头列表
     *
     * @param ids 编号
     * @return 客户发票抬头列表
     */
    List<CrmReceiptHead> getCrmReceiptHeadList(Collection<Long> ids);

    /**
     * 获得客户发票抬头分页
     *
     * @param pageReqVO 分页查询
     * @return 客户发票抬头分页
     */
    PageResult<CrmReceiptHead> getCrmReceiptHeadPage(CrmReceiptHeadPageReqVO pageReqVO, CrmReceiptHeadPageOrder orderV0);

    /**
     * 获得客户发票抬头列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 客户发票抬头列表
     */
    List<CrmReceiptHead> getCrmReceiptHeadList(CrmReceiptHeadExportReqVO exportReqVO);

}
