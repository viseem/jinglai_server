package cn.iocoder.yudao.module.jl.service.project;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.SupplySendIn;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 物资寄来单申请 Service 接口
 *
 */
public interface SupplySendInService {

    /**
     * 创建物资寄来单申请
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSupplySendIn(@Valid SupplySendInCreateReqVO createReqVO);

    /**
     * 更新物资寄来单申请
     *
     * @param updateReqVO 更新信息
     */
    void updateSupplySendIn(@Valid SupplySendInUpdateReqVO updateReqVO);

    void saveSupplySendIn(@Valid SupplySendInSaveReqVO updateReqVO);

    /**
     * 删除物资寄来单申请
     *
     * @param id 编号
     */
    void deleteSupplySendIn(Long id);

    /**
     * 获得物资寄来单申请
     *
     * @param id 编号
     * @return 物资寄来单申请
     */
    Optional<SupplySendIn> getSupplySendIn(Long id);

    /**
     * 获得物资寄来单申请列表
     *
     * @param ids 编号
     * @return 物资寄来单申请列表
     */
    List<SupplySendIn> getSupplySendInList(Collection<Long> ids);

    /**
     * 获得物资寄来单申请分页
     *
     * @param pageReqVO 分页查询
     * @return 物资寄来单申请分页
     */
    PageResult<SupplySendIn> getSupplySendInPage(SupplySendInPageReqVO pageReqVO, SupplySendInPageOrder orderV0);

    /**
     * 获得物资寄来单申请列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 物资寄来单申请列表
     */
    List<SupplySendIn> getSupplySendInList(SupplySendInExportReqVO exportReqVO);

    void checkIn(SendInCheckInReqVO saveReqVO);

    void storeIn(StoreInSendInItemReqVO saveReqVO);
}
