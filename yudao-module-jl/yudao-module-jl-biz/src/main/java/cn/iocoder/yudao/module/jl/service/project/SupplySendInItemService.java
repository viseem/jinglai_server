package cn.iocoder.yudao.module.jl.service.project;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.SupplySendInItem;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 物资寄来单申请明细 Service 接口
 *
 */
public interface SupplySendInItemService {

    /**
     * 创建物资寄来单申请明细
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSupplySendInItem(@Valid SupplySendInItemCreateReqVO createReqVO);

    /**
     * 更新物资寄来单申请明细
     *
     * @param updateReqVO 更新信息
     */
    void updateSupplySendInItem(@Valid SupplySendInItemUpdateReqVO updateReqVO);

    /**
     * 删除物资寄来单申请明细
     *
     * @param id 编号
     */
    void deleteSupplySendInItem(Long id);

    /**
     * 获得物资寄来单申请明细
     *
     * @param id 编号
     * @return 物资寄来单申请明细
     */
    Optional<SupplySendInItem> getSupplySendInItem(Long id);

    /**
     * 获得物资寄来单申请明细列表
     *
     * @param ids 编号
     * @return 物资寄来单申请明细列表
     */
    List<SupplySendInItem> getSupplySendInItemList(Collection<Long> ids);

    /**
     * 获得物资寄来单申请明细分页
     *
     * @param pageReqVO 分页查询
     * @return 物资寄来单申请明细分页
     */
    PageResult<SupplySendInItem> getSupplySendInItemPage(SupplySendInItemPageReqVO pageReqVO, SupplySendInItemPageOrder orderV0);

    /**
     * 获得物资寄来单申请明细列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 物资寄来单申请明细列表
     */
    List<SupplySendInItem> getSupplySendInItemList(SupplySendInItemExportReqVO exportReqVO);

}
