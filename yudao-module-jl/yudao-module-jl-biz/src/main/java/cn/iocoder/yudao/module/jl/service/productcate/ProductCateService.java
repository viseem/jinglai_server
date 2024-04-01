package cn.iocoder.yudao.module.jl.service.productcate;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.productcate.vo.*;
import cn.iocoder.yudao.module.jl.entity.productcate.ProductCate;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 产品库分类 Service 接口
 *
 */
public interface ProductCateService {

    /**
     * 创建产品库分类
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProductCate(@Valid ProductCateCreateReqVO createReqVO);

    /**
     * 更新产品库分类
     *
     * @param updateReqVO 更新信息
     */
    void updateProductCate(@Valid ProductCateUpdateReqVO updateReqVO);

    /**
     * 删除产品库分类
     *
     * @param id 编号
     */
    void deleteProductCate(Long id);

    /**
     * 获得产品库分类
     *
     * @param id 编号
     * @return 产品库分类
     */
    Optional<ProductCate> getProductCate(Long id);

    /**
     * 获得产品库分类列表
     *
     * @param ids 编号
     * @return 产品库分类列表
     */
    List<ProductCate> getProductCateList(Collection<Long> ids);

    /**
     * 获得产品库分类分页
     *
     * @param pageReqVO 分页查询
     * @return 产品库分类分页
     */
    PageResult<ProductCate> getProductCatePage(ProductCatePageReqVO pageReqVO, ProductCatePageOrder orderV0);

    /**
     * 获得产品库分类列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 产品库分类列表
     */
    List<ProductCate> getProductCateList(ProductCateExportReqVO exportReqVO);

}
