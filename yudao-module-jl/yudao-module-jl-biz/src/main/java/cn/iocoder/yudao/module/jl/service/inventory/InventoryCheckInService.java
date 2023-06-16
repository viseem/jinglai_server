package cn.iocoder.yudao.module.jl.service.inventory;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryCheckIn;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 签收记录 Service 接口
 *
 */
public interface InventoryCheckInService {

    /**
     * 创建签收记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createInventoryCheckIn(@Valid InventoryCheckInCreateReqVO createReqVO);

    /**
     * 更新签收记录
     *
     * @param updateReqVO 更新信息
     */
    void updateInventoryCheckIn(@Valid InventoryCheckInUpdateReqVO updateReqVO);

    /**
     * 删除签收记录
     *
     * @param id 编号
     */
    void deleteInventoryCheckIn(Long id);

    /**
     * 获得签收记录
     *
     * @param id 编号
     * @return 签收记录
     */
    Optional<InventoryCheckIn> getInventoryCheckIn(Long id);

    /**
     * 获得签收记录列表
     *
     * @param ids 编号
     * @return 签收记录列表
     */
    List<InventoryCheckIn> getInventoryCheckInList(Collection<Long> ids);

    /**
     * 获得签收记录分页
     *
     * @param pageReqVO 分页查询
     * @return 签收记录分页
     */
    PageResult<InventoryCheckIn> getInventoryCheckInPage(InventoryCheckInPageReqVO pageReqVO, InventoryCheckInPageOrder orderV0);

    /**
     * 获得签收记录列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 签收记录列表
     */
    List<InventoryCheckIn> getInventoryCheckInList(InventoryCheckInExportReqVO exportReqVO);

}
