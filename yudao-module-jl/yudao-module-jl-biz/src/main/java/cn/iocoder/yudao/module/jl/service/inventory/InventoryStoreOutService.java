package cn.iocoder.yudao.module.jl.service.inventory;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryStoreOut;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 出库记录 Service 接口
 *
 */
public interface InventoryStoreOutService {

    /**
     * 创建出库记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createInventoryStoreOut(@Valid InventoryStoreOutCreateReqVO createReqVO);

    /**
     * 更新出库记录
     *
     * @param updateReqVO 更新信息
     */
    void updateInventoryStoreOut(@Valid InventoryStoreOutUpdateReqVO updateReqVO);

    /**
     * 删除出库记录
     *
     * @param id 编号
     */
    void deleteInventoryStoreOut(Long id);

    /**
     * 获得出库记录
     *
     * @param id 编号
     * @return 出库记录
     */
    Optional<InventoryStoreOut> getInventoryStoreOut(Long id);

    /**
     * 获得出库记录列表
     *
     * @param ids 编号
     * @return 出库记录列表
     */
    List<InventoryStoreOut> getInventoryStoreOutList(Collection<Long> ids);

    /**
     * 获得出库记录分页
     *
     * @param pageReqVO 分页查询
     * @return 出库记录分页
     */
    PageResult<InventoryStoreOut> getInventoryStoreOutPage(InventoryStoreOutPageReqVO pageReqVO, InventoryStoreOutPageOrder orderV0);

    /**
     * 获得出库记录列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 出库记录列表
     */
    List<InventoryStoreOut> getInventoryStoreOutList(InventoryStoreOutExportReqVO exportReqVO);

}
