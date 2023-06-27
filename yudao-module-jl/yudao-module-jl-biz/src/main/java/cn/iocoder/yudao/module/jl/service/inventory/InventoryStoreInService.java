package cn.iocoder.yudao.module.jl.service.inventory;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryStoreIn;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 入库记录 Service 接口
 *
 */
public interface InventoryStoreInService {

    /**
     * 创建入库记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createInventoryStoreIn(@Valid InventoryStoreInCreateReqVO createReqVO);

    /**
     * 更新入库记录
     *
     * @param updateReqVO 更新信息
     */
    void updateInventoryStoreIn(@Valid InventoryStoreInUpdateReqVO updateReqVO);

    /**
     * 删除入库记录
     *
     * @param id 编号
     */
    void deleteInventoryStoreIn(Long id);

    /**
     * 获得入库记录
     *
     * @param id 编号
     * @return 入库记录
     */
    Optional<InventoryStoreIn> getInventoryStoreIn(Long id);

    /**
     * 获得入库记录列表
     *
     * @param ids 编号
     * @return 入库记录列表
     */
    List<InventoryStoreIn> getInventoryStoreInList(Collection<Long> ids);

    /**
     * 获得入库记录分页
     *
     * @param pageReqVO 分页查询
     * @return 入库记录分页
     */
    PageResult<InventoryStoreIn> getInventoryStoreInPage(InventoryStoreInPageReqVO pageReqVO, InventoryStoreInPageOrder orderV0);

    /**
     * 获得入库记录列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 入库记录列表
     */
    List<InventoryStoreIn> getInventoryStoreInList(InventoryStoreInExportReqVO exportReqVO);

}
