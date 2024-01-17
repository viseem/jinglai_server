package cn.iocoder.yudao.module.jl.service.shipwarehouse;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.shipwarehouse.vo.*;
import cn.iocoder.yudao.module.jl.entity.shipwarehouse.ShipWarehouse;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 收货仓库 Service 接口
 *
 */
public interface ShipWarehouseService {

    /**
     * 创建收货仓库
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createShipWarehouse(@Valid ShipWarehouseCreateReqVO createReqVO);

    /**
     * 更新收货仓库
     *
     * @param updateReqVO 更新信息
     */
    void updateShipWarehouse(@Valid ShipWarehouseUpdateReqVO updateReqVO);

    /**
     * 删除收货仓库
     *
     * @param id 编号
     */
    void deleteShipWarehouse(Long id);

    /**
     * 获得收货仓库
     *
     * @param id 编号
     * @return 收货仓库
     */
    Optional<ShipWarehouse> getShipWarehouse(Long id);

    /**
     * 获得收货仓库列表
     *
     * @param ids 编号
     * @return 收货仓库列表
     */
    List<ShipWarehouse> getShipWarehouseList(Collection<Long> ids);

    /**
     * 获得收货仓库分页
     *
     * @param pageReqVO 分页查询
     * @return 收货仓库分页
     */
    PageResult<ShipWarehouse> getShipWarehousePage(ShipWarehousePageReqVO pageReqVO, ShipWarehousePageOrder orderV0);

    /**
     * 获得收货仓库列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 收货仓库列表
     */
    List<ShipWarehouse> getShipWarehouseList(ShipWarehouseExportReqVO exportReqVO);

}
