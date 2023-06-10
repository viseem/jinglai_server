package cn.iocoder.yudao.module.jl.service.inventory;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.ProductInItem;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 实验产品入库明细 Service 接口
 *
 */
public interface ProductInItemService {

    /**
     * 创建实验产品入库明细
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProductInItem(@Valid ProductInItemCreateReqVO createReqVO);

    /**
     * 更新实验产品入库明细
     *
     * @param updateReqVO 更新信息
     */
    void updateProductInItem(@Valid ProductInItemUpdateReqVO updateReqVO);

    /**
     * 删除实验产品入库明细
     *
     * @param id 编号
     */
    void deleteProductInItem(Long id);

    /**
     * 获得实验产品入库明细
     *
     * @param id 编号
     * @return 实验产品入库明细
     */
    Optional<ProductInItem> getProductInItem(Long id);

    /**
     * 获得实验产品入库明细列表
     *
     * @param ids 编号
     * @return 实验产品入库明细列表
     */
    List<ProductInItem> getProductInItemList(Collection<Long> ids);

    /**
     * 获得实验产品入库明细分页
     *
     * @param pageReqVO 分页查询
     * @return 实验产品入库明细分页
     */
    PageResult<ProductInItem> getProductInItemPage(ProductInItemPageReqVO pageReqVO, ProductInItemPageOrder orderV0);

    /**
     * 获得实验产品入库明细列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 实验产品入库明细列表
     */
    List<ProductInItem> getProductInItemList(ProductInItemExportReqVO exportReqVO);

}
