package cn.iocoder.yudao.module.jl.service.inventoryproductlog;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.inventoryproductlog.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventoryproductlog.InventoryProductLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 产品变更日志 Service 接口
 *
 */
public interface InventoryProductLogService {

    /**
     * 创建产品变更日志
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createInventoryProductLog(@Valid InventoryProductLogCreateReqVO createReqVO);

    /**
     * 更新产品变更日志
     *
     * @param updateReqVO 更新信息
     */
    void updateInventoryProductLog(@Valid InventoryProductLogUpdateReqVO updateReqVO);

    /**
     * 删除产品变更日志
     *
     * @param id 编号
     */
    void deleteInventoryProductLog(Long id);

    /**
     * 获得产品变更日志
     *
     * @param id 编号
     * @return 产品变更日志
     */
    Optional<InventoryProductLog> getInventoryProductLog(Long id);

    /**
     * 获得产品变更日志列表
     *
     * @param ids 编号
     * @return 产品变更日志列表
     */
    List<InventoryProductLog> getInventoryProductLogList(Collection<Long> ids);

    /**
     * 获得产品变更日志分页
     *
     * @param pageReqVO 分页查询
     * @return 产品变更日志分页
     */
    PageResult<InventoryProductLog> getInventoryProductLogPage(InventoryProductLogPageReqVO pageReqVO, InventoryProductLogPageOrder orderV0);

    /**
     * 获得产品变更日志列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 产品变更日志列表
     */
    List<InventoryProductLog> getInventoryProductLogList(InventoryProductLogExportReqVO exportReqVO);

}
