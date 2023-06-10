package cn.iocoder.yudao.module.jl.service.inventory;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.InventoryRoom;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 库管房间号 Service 接口
 *
 */
public interface InventoryRoomService {

    /**
     * 创建库管房间号
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createInventoryRoom(@Valid InventoryRoomCreateReqVO createReqVO);

    /**
     * 更新库管房间号
     *
     * @param updateReqVO 更新信息
     */
    void updateInventoryRoom(@Valid InventoryRoomUpdateReqVO updateReqVO);

    /**
     * 删除库管房间号
     *
     * @param id 编号
     */
    void deleteInventoryRoom(Long id);

    /**
     * 获得库管房间号
     *
     * @param id 编号
     * @return 库管房间号
     */
    Optional<InventoryRoom> getInventoryRoom(Long id);

    /**
     * 获得库管房间号列表
     *
     * @param ids 编号
     * @return 库管房间号列表
     */
    List<InventoryRoom> getInventoryRoomList(Collection<Long> ids);

    /**
     * 获得库管房间号分页
     *
     * @param pageReqVO 分页查询
     * @return 库管房间号分页
     */
    PageResult<InventoryRoom> getInventoryRoomPage(InventoryRoomPageReqVO pageReqVO, InventoryRoomPageOrder orderV0);

    /**
     * 获得库管房间号列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 库管房间号列表
     */
    List<InventoryRoom> getInventoryRoomList(InventoryRoomExportReqVO exportReqVO);

}
