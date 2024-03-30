package cn.iocoder.yudao.module.jl.service.productuser;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.productuser.vo.*;
import cn.iocoder.yudao.module.jl.entity.productuser.ProductUser;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 产品库人员 Service 接口
 *
 */
public interface ProductUserService {

    /**
     * 创建产品库人员
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProductUser(@Valid ProductUserCreateReqVO createReqVO);

    /**
     * 更新产品库人员
     *
     * @param updateReqVO 更新信息
     */
    void updateProductUser(@Valid ProductUserUpdateReqVO updateReqVO);

    /**
     * 删除产品库人员
     *
     * @param id 编号
     */
    void deleteProductUser(Long id);

    /**
     * 获得产品库人员
     *
     * @param id 编号
     * @return 产品库人员
     */
    Optional<ProductUser> getProductUser(Long id);

    /**
     * 获得产品库人员列表
     *
     * @param ids 编号
     * @return 产品库人员列表
     */
    List<ProductUser> getProductUserList(Collection<Long> ids);

    /**
     * 获得产品库人员分页
     *
     * @param pageReqVO 分页查询
     * @return 产品库人员分页
     */
    PageResult<ProductUser> getProductUserPage(ProductUserPageReqVO pageReqVO, ProductUserPageOrder orderV0);

    /**
     * 获得产品库人员列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 产品库人员列表
     */
    List<ProductUser> getProductUserList(ProductUserExportReqVO exportReqVO);

}
