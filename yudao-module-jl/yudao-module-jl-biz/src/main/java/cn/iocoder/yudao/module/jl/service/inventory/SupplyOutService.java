package cn.iocoder.yudao.module.jl.service.inventory;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.SupplyOut;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 出库申请 Service 接口
 *
 */
public interface SupplyOutService {

    /**
     * 创建出库申请
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSupplyOut(@Valid SupplyOutCreateReqVO createReqVO);

    /**
     * 批量创建出库申请
     *
     * @param saveReqVO 创建信息
     * @return 编号
     */
    Long saveSupplyOut(@Valid SupplyOutSaveReqVO saveReqVO);

    /**
     * 更新出库申请
     *
     * @param updateReqVO 更新信息
     */
    void updateSupplyOut(@Valid SupplyOutUpdateReqVO updateReqVO);

    /**
     * 删除出库申请
     *
     * @param id 编号
     */
    void deleteSupplyOut(Long id);

    /**
     * 获得出库申请
     *
     * @param id 编号
     * @return 出库申请
     */
    Optional<SupplyOut> getSupplyOut(Long id);

    /**
     * 获得出库申请列表
     *
     * @param ids 编号
     * @return 出库申请列表
     */
    List<SupplyOut> getSupplyOutList(Collection<Long> ids);

    /**
     * 获得出库申请分页
     *
     * @param pageReqVO 分页查询
     * @return 出库申请分页
     */
    PageResult<SupplyOut> getSupplyOutPage(SupplyOutPageReqVO pageReqVO, SupplyOutPageOrder orderV0);

    /**
     * 获得出库申请列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 出库申请列表
     */
    List<SupplyOut> getSupplyOutList(SupplyOutExportReqVO exportReqVO);

}
