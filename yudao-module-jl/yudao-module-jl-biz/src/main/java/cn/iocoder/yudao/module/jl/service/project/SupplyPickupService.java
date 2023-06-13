package cn.iocoder.yudao.module.jl.service.project;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.SupplyPickup;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 取货单申请 Service 接口
 *
 */
public interface SupplyPickupService {

    /**
     * 创建取货单申请
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSupplyPickup(@Valid SupplyPickupCreateReqVO createReqVO);

    /**
     * 更新取货单申请
     *
     * @param updateReqVO 更新信息
     */
    void updateSupplyPickup(@Valid SupplyPickupUpdateReqVO updateReqVO);

    /**
     * 删除取货单申请
     *
     * @param id 编号
     */
    void deleteSupplyPickup(Long id);

    /**
     * 获得取货单申请
     *
     * @param id 编号
     * @return 取货单申请
     */
    Optional<SupplyPickup> getSupplyPickup(Long id);

    /**
     * 获得取货单申请列表
     *
     * @param ids 编号
     * @return 取货单申请列表
     */
    List<SupplyPickup> getSupplyPickupList(Collection<Long> ids);

    /**
     * 获得取货单申请分页
     *
     * @param pageReqVO 分页查询
     * @return 取货单申请分页
     */
    PageResult<SupplyPickup> getSupplyPickupPage(SupplyPickupPageReqVO pageReqVO, SupplyPickupPageOrder orderV0);

    /**
     * 获得取货单申请列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 取货单申请列表
     */
    List<SupplyPickup> getSupplyPickupList(SupplyPickupExportReqVO exportReqVO);

    void saveSupplyPickup(SupplyPickupSaveReqVO saveReqVO);
}
