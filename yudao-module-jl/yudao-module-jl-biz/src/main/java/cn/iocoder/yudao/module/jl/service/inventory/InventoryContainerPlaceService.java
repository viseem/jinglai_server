package cn.iocoder.yudao.module.jl.service.inventory;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryContainerPlace;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 库管存储容器位置 Service 接口
 *
 */
public interface InventoryContainerPlaceService {

    /**
     * 创建库管存储容器位置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createInventoryContainerPlace(@Valid InventoryContainerPlaceCreateReqVO createReqVO);

    /**
     * 更新库管存储容器位置
     *
     * @param updateReqVO 更新信息
     */
    void updateInventoryContainerPlace(@Valid InventoryContainerPlaceUpdateReqVO updateReqVO);

    /**
     * 删除库管存储容器位置
     *
     * @param id 编号
     */
    void deleteInventoryContainerPlace(Long id);

    /**
     * 获得库管存储容器位置
     *
     * @param id 编号
     * @return 库管存储容器位置
     */
    Optional<InventoryContainerPlace> getInventoryContainerPlace(Long id);

    /**
     * 获得库管存储容器位置列表
     *
     * @param ids 编号
     * @return 库管存储容器位置列表
     */
    List<InventoryContainerPlace> getInventoryContainerPlaceList(Collection<Long> ids);

    /**
     * 获得库管存储容器位置分页
     *
     * @param pageReqVO 分页查询
     * @return 库管存储容器位置分页
     */
    PageResult<InventoryContainerPlace> getInventoryContainerPlacePage(InventoryContainerPlacePageReqVO pageReqVO, InventoryContainerPlacePageOrder orderV0);

    /**
     * 获得库管存储容器位置列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 库管存储容器位置列表
     */
    List<InventoryContainerPlace> getInventoryContainerPlaceList(InventoryContainerPlaceExportReqVO exportReqVO);

}
