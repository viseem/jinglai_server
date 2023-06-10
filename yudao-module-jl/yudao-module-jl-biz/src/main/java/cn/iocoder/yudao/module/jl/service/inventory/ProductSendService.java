package cn.iocoder.yudao.module.jl.service.inventory;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.ProductSend;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 实验产品寄送 Service 接口
 *
 */
public interface ProductSendService {

    /**
     * 创建实验产品寄送
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProductSend(@Valid ProductSendCreateReqVO createReqVO);

    /**
     * 更新实验产品寄送
     *
     * @param updateReqVO 更新信息
     */
    void updateProductSend(@Valid ProductSendUpdateReqVO updateReqVO);

    /**
     * 删除实验产品寄送
     *
     * @param id 编号
     */
    void deleteProductSend(Long id);

    /**
     * 获得实验产品寄送
     *
     * @param id 编号
     * @return 实验产品寄送
     */
    Optional<ProductSend> getProductSend(Long id);

    /**
     * 获得实验产品寄送列表
     *
     * @param ids 编号
     * @return 实验产品寄送列表
     */
    List<ProductSend> getProductSendList(Collection<Long> ids);

    /**
     * 获得实验产品寄送分页
     *
     * @param pageReqVO 分页查询
     * @return 实验产品寄送分页
     */
    PageResult<ProductSend> getProductSendPage(ProductSendPageReqVO pageReqVO, ProductSendPageOrder orderV0);

    /**
     * 获得实验产品寄送列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 实验产品寄送列表
     */
    List<ProductSend> getProductSendList(ProductSendExportReqVO exportReqVO);

}
