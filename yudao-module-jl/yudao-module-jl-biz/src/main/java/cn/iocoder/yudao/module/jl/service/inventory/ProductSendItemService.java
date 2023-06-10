package cn.iocoder.yudao.module.jl.service.inventory;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.ProductSendItem;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 实验产品寄送明细 Service 接口
 *
 */
public interface ProductSendItemService {

    /**
     * 创建实验产品寄送明细
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProductSendItem(@Valid ProductSendItemCreateReqVO createReqVO);

    /**
     * 更新实验产品寄送明细
     *
     * @param updateReqVO 更新信息
     */
    void updateProductSendItem(@Valid ProductSendItemUpdateReqVO updateReqVO);

    /**
     * 删除实验产品寄送明细
     *
     * @param id 编号
     */
    void deleteProductSendItem(Long id);

    /**
     * 获得实验产品寄送明细
     *
     * @param id 编号
     * @return 实验产品寄送明细
     */
    Optional<ProductSendItem> getProductSendItem(Long id);

    /**
     * 获得实验产品寄送明细列表
     *
     * @param ids 编号
     * @return 实验产品寄送明细列表
     */
    List<ProductSendItem> getProductSendItemList(Collection<Long> ids);

    /**
     * 获得实验产品寄送明细分页
     *
     * @param pageReqVO 分页查询
     * @return 实验产品寄送明细分页
     */
    PageResult<ProductSendItem> getProductSendItemPage(ProductSendItemPageReqVO pageReqVO, ProductSendItemPageOrder orderV0);

    /**
     * 获得实验产品寄送明细列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 实验产品寄送明细列表
     */
    List<ProductSendItem> getProductSendItemList(ProductSendItemExportReqVO exportReqVO);

}
