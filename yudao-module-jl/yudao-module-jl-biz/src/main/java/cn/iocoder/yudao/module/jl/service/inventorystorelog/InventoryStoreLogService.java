package cn.iocoder.yudao.module.jl.service.inventorystorelog;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.inventorystorelog.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventorystorelog.InventoryStoreLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 物品出入库日志 Service 接口
 *
 */
public interface InventoryStoreLogService {

    /**
     * 创建物品出入库日志
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createInventoryStoreLog(@Valid InventoryStoreLogCreateReqVO createReqVO);

    /**
     * 更新物品出入库日志
     *
     * @param updateReqVO 更新信息
     */
    void updateInventoryStoreLog(@Valid InventoryStoreLogUpdateReqVO updateReqVO);

    /**
     * 删除物品出入库日志
     *
     * @param id 编号
     */
    void deleteInventoryStoreLog(Long id);

    /**
     * 获得物品出入库日志
     *
     * @param id 编号
     * @return 物品出入库日志
     */
    Optional<InventoryStoreLog> getInventoryStoreLog(Long id);

    /**
     * 获得物品出入库日志列表
     *
     * @param ids 编号
     * @return 物品出入库日志列表
     */
    List<InventoryStoreLog> getInventoryStoreLogList(Collection<Long> ids);

    /**
     * 获得物品出入库日志分页
     *
     * @param pageReqVO 分页查询
     * @return 物品出入库日志分页
     */
    PageResult<InventoryStoreLog> getInventoryStoreLogPage(InventoryStoreLogPageReqVO pageReqVO, InventoryStoreLogPageOrder orderV0);

    /**
     * 获得物品出入库日志列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 物品出入库日志列表
     */
    List<InventoryStoreLog> getInventoryStoreLogList(InventoryStoreLogExportReqVO exportReqVO);

}
