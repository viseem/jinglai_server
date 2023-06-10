package cn.iocoder.yudao.module.jl.service.project;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.SupplyPickupItem;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 取货单申请明细 Service 接口
 *
 */
public interface SupplyPickupItemService {

    /**
     * 创建取货单申请明细
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSupplyPickupItem(@Valid SupplyPickupItemCreateReqVO createReqVO);

    /**
     * 更新取货单申请明细
     *
     * @param updateReqVO 更新信息
     */
    void updateSupplyPickupItem(@Valid SupplyPickupItemUpdateReqVO updateReqVO);

    /**
     * 删除取货单申请明细
     *
     * @param id 编号
     */
    void deleteSupplyPickupItem(Long id);

    /**
     * 获得取货单申请明细
     *
     * @param id 编号
     * @return 取货单申请明细
     */
    Optional<SupplyPickupItem> getSupplyPickupItem(Long id);

    /**
     * 获得取货单申请明细列表
     *
     * @param ids 编号
     * @return 取货单申请明细列表
     */
    List<SupplyPickupItem> getSupplyPickupItemList(Collection<Long> ids);

    /**
     * 获得取货单申请明细分页
     *
     * @param pageReqVO 分页查询
     * @return 取货单申请明细分页
     */
    PageResult<SupplyPickupItem> getSupplyPickupItemPage(SupplyPickupItemPageReqVO pageReqVO, SupplyPickupItemPageOrder orderV0);

    /**
     * 获得取货单申请明细列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 取货单申请明细列表
     */
    List<SupplyPickupItem> getSupplyPickupItemList(SupplyPickupItemExportReqVO exportReqVO);

}
