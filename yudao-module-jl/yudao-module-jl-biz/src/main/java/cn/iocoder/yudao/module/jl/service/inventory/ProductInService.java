package cn.iocoder.yudao.module.jl.service.inventory;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.ProductIn;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 实验产品入库 Service 接口
 *
 */
public interface ProductInService {

    /**
     * 创建实验产品入库
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProductIn(@Valid ProductInCreateReqVO createReqVO);

    /**
     * 更新实验产品入库
     *
     * @param updateReqVO 更新信息
     */
    void updateProductIn(@Valid ProductInUpdateReqVO updateReqVO);

    /**
     * 删除实验产品入库
     *
     * @param id 编号
     */
    void deleteProductIn(Long id);

    /**
     * 获得实验产品入库
     *
     * @param id 编号
     * @return 实验产品入库
     */
    Optional<ProductIn> getProductIn(Long id);

    /**
     * 获得实验产品入库列表
     *
     * @param ids 编号
     * @return 实验产品入库列表
     */
    List<ProductIn> getProductInList(Collection<Long> ids);

    /**
     * 获得实验产品入库分页
     *
     * @param pageReqVO 分页查询
     * @return 实验产品入库分页
     */
    PageResult<ProductIn> getProductInPage(ProductInPageReqVO pageReqVO, ProductInPageOrder orderV0);

    /**
     * 获得实验产品入库列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 实验产品入库列表
     */
    List<ProductIn> getProductInList(ProductInExportReqVO exportReqVO);

}
