package cn.iocoder.yudao.module.jl.service.inventory;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.SupplyOutItem;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 出库申请明细 Service 接口
 *
 */
public interface SupplyOutItemService {

    /**
     * 创建出库申请明细
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSupplyOutItem(@Valid SupplyOutItemCreateReqVO createReqVO);

    /**
     * 更新出库申请明细
     *
     * @param updateReqVO 更新信息
     */
    void updateSupplyOutItem(@Valid SupplyOutItemUpdateReqVO updateReqVO);

    /**
     * 删除出库申请明细
     *
     * @param id 编号
     */
    void deleteSupplyOutItem(Long id);

    /**
     * 获得出库申请明细
     *
     * @param id 编号
     * @return 出库申请明细
     */
    Optional<SupplyOutItem> getSupplyOutItem(Long id);

    /**
     * 获得出库申请明细列表
     *
     * @param ids 编号
     * @return 出库申请明细列表
     */
    List<SupplyOutItem> getSupplyOutItemList(Collection<Long> ids);

    /**
     * 获得出库申请明细分页
     *
     * @param pageReqVO 分页查询
     * @return 出库申请明细分页
     */
    PageResult<SupplyOutItem> getSupplyOutItemPage(SupplyOutItemPageReqVO pageReqVO, SupplyOutItemPageOrder orderV0);

    /**
     * 获得出库申请明细列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 出库申请明细列表
     */
    List<SupplyOutItem> getSupplyOutItemList(SupplyOutItemExportReqVO exportReqVO);

}
