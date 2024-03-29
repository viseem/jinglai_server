package cn.iocoder.yudao.module.jl.service.purchasecontract;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.purchasecontract.vo.*;
import cn.iocoder.yudao.module.jl.entity.purchasecontract.PurchaseContract;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 购销合同 Service 接口
 *
 */
public interface PurchaseContractService {

    /**
     * 创建购销合同
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPurchaseContract(@Valid PurchaseContractCreateReqVO createReqVO);

    /**
     * 创建购销合同和明细
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long savePurchaseContract(@Valid PurchaseContractSaveReqVO createReqVO);

    /**
     * 更新购销合同
     *
     * @param updateReqVO 更新信息
     */
    void updatePurchaseContract(@Valid PurchaseContractUpdateReqVO updateReqVO);

    /**
     * 删除购销合同
     *
     * @param id 编号
     */
    void deletePurchaseContract(Long id);

    /**
     * 获得购销合同
     *
     * @param id 编号
     * @return 购销合同
     */
    Optional<PurchaseContract> getPurchaseContract(Long id);

    /**
     * 获得购销合同列表
     *
     * @param ids 编号
     * @return 购销合同列表
     */
    List<PurchaseContract> getPurchaseContractList(Collection<Long> ids);

    /**
     * 获得购销合同分页
     *
     * @param pageReqVO 分页查询
     * @return 购销合同分页
     */
    PageResult<PurchaseContract> getPurchaseContractPage(PurchaseContractPageReqVO pageReqVO, PurchaseContractPageOrder orderV0);

    /**
     * 获得购销合同列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 购销合同列表
     */
    List<PurchaseContract> getPurchaseContractList(PurchaseContractExportReqVO exportReqVO);

}
