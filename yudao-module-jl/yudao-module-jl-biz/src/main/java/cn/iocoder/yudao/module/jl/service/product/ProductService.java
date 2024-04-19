package cn.iocoder.yudao.module.jl.service.product;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.product.vo.*;
import cn.iocoder.yudao.module.jl.entity.product.Product;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.entity.product.ProductDetail;

/**
 * 产品库 Service 接口
 *
 */
public interface ProductService {

    /**
     * 创建产品库
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProduct(@Valid ProductCreateReqVO createReqVO);

    /**
     * 更新产品库
     *
     * @param updateReqVO 更新信息
     */
    void updateProduct(@Valid ProductUpdateReqVO updateReqVO);

    /**
     * 删除产品库
     *
     * @param id 编号
     */
    void deleteProduct(Long id);

    /**
     * 获得产品库
     *
     * @param id 编号
     * @return 产品库
     */
    Optional<ProductDetail> getProduct(Long id);

    /**
     * 获得产品库列表
     *
     * @param ids 编号
     * @return 产品库列表
     */
    List<Product> getProductList(Collection<Long> ids);

    /**
     * 获得产品库分页
     *
     * @param pageReqVO 分页查询
     * @return 产品库分页
     */
    PageResult<Product> getProductPage(ProductPageReqVO pageReqVO, ProductPageOrder orderV0);

    /**
     * 获得产品库列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 产品库列表
     */
    List<Product> getProductList(ProductExportReqVO exportReqVO);

}
