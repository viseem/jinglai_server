package cn.iocoder.yudao.module.jl.service.productsop;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.productsop.vo.*;
import cn.iocoder.yudao.module.jl.entity.productsop.ProductSop;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 产品sop Service 接口
 *
 */
public interface ProductSopService {

    /**
     * 创建产品sop
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProductSop(@Valid ProductSopCreateReqVO createReqVO);

    /**
     * 更新产品sop
     *
     * @param updateReqVO 更新信息
     */
    void updateProductSop(@Valid ProductSopUpdateReqVO updateReqVO);

    /**
     * 删除产品sop
     *
     * @param id 编号
     */
    void deleteProductSop(Long id);

    /**
     * 获得产品sop
     *
     * @param id 编号
     * @return 产品sop
     */
    Optional<ProductSop> getProductSop(Long id);

    /**
     * 获得产品sop列表
     *
     * @param ids 编号
     * @return 产品sop列表
     */
    List<ProductSop> getProductSopList(Collection<Long> ids);

    /**
     * 获得产品sop分页
     *
     * @param pageReqVO 分页查询
     * @return 产品sop分页
     */
    PageResult<ProductSop> getProductSopPage(ProductSopPageReqVO pageReqVO, ProductSopPageOrder orderV0);

    /**
     * 获得产品sop列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 产品sop列表
     */
    List<ProductSop> getProductSopList(ProductSopExportReqVO exportReqVO);

}
