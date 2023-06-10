package cn.iocoder.yudao.module.jl.service.inventory;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryContainer;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 库管存储容器 Service 接口
 *
 */
public interface InventoryContainerService {

    /**
     * 创建库管存储容器
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createInventoryContainer(@Valid InventoryContainerCreateReqVO createReqVO);

    /**
     * 更新库管存储容器
     *
     * @param updateReqVO 更新信息
     */
    void updateInventoryContainer(@Valid InventoryContainerUpdateReqVO updateReqVO);

    /**
     * 删除库管存储容器
     *
     * @param id 编号
     */
    void deleteInventoryContainer(Long id);

    /**
     * 获得库管存储容器
     *
     * @param id 编号
     * @return 库管存储容器
     */
    Optional<InventoryContainer> getInventoryContainer(Long id);

    /**
     * 获得库管存储容器列表
     *
     * @param ids 编号
     * @return 库管存储容器列表
     */
    List<InventoryContainer> getInventoryContainerList(Collection<Long> ids);

    /**
     * 获得库管存储容器分页
     *
     * @param pageReqVO 分页查询
     * @return 库管存储容器分页
     */
    PageResult<InventoryContainer> getInventoryContainerPage(InventoryContainerPageReqVO pageReqVO, InventoryContainerPageOrder orderV0);

    /**
     * 获得库管存储容器列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 库管存储容器列表
     */
    List<InventoryContainer> getInventoryContainerList(InventoryContainerExportReqVO exportReqVO);

}
