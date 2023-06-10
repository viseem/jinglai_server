package cn.iocoder.yudao.module.jl.service.project;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.ProcurementItem;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 项目采购单申请明细 Service 接口
 *
 */
public interface ProcurementItemService {

    /**
     * 创建项目采购单申请明细
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProcurementItem(@Valid ProcurementItemCreateReqVO createReqVO);

    /**
     * 更新项目采购单申请明细
     *
     * @param updateReqVO 更新信息
     */
    void updateProcurementItem(@Valid ProcurementItemUpdateReqVO updateReqVO);

    /**
     * 删除项目采购单申请明细
     *
     * @param id 编号
     */
    void deleteProcurementItem(Long id);

    /**
     * 获得项目采购单申请明细
     *
     * @param id 编号
     * @return 项目采购单申请明细
     */
    Optional<ProcurementItem> getProcurementItem(Long id);

    /**
     * 获得项目采购单申请明细列表
     *
     * @param ids 编号
     * @return 项目采购单申请明细列表
     */
    List<ProcurementItem> getProcurementItemList(Collection<Long> ids);

    /**
     * 获得项目采购单申请明细分页
     *
     * @param pageReqVO 分页查询
     * @return 项目采购单申请明细分页
     */
    PageResult<ProcurementItem> getProcurementItemPage(ProcurementItemPageReqVO pageReqVO, ProcurementItemPageOrder orderV0);

    /**
     * 获得项目采购单申请明细列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 项目采购单申请明细列表
     */
    List<ProcurementItem> getProcurementItemList(ProcurementItemExportReqVO exportReqVO);

}
