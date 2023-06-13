package cn.iocoder.yudao.module.jl.service.project;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.Procurement;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 项目采购单申请 Service 接口
 *
 */
public interface ProcurementService {

    /**
     * 创建项目采购单申请
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProcurement(@Valid ProcurementCreateReqVO createReqVO);

    /**
     * 更新项目采购单申请
     *
     * @param updateReqVO 更新信息
     */
    void updateProcurement(@Valid ProcurementUpdateReqVO updateReqVO);

    void saveProcurement(@Valid ProcurementSaveReqVO updateReqVO);

    /**
     * 删除项目采购单申请
     *
     * @param id 编号
     */
    void deleteProcurement(Long id);

    /**
     * 获得项目采购单申请
     *
     * @param id 编号
     * @return 项目采购单申请
     */
    Optional<Procurement> getProcurement(Long id);

    /**
     * 获得项目采购单申请列表
     *
     * @param ids 编号
     * @return 项目采购单申请列表
     */
    List<Procurement> getProcurementList(Collection<Long> ids);

    /**
     * 获得项目采购单申请分页
     *
     * @param pageReqVO 分页查询
     * @return 项目采购单申请分页
     */
    PageResult<Procurement> getProcurementPage(ProcurementPageReqVO pageReqVO, ProcurementPageOrder orderV0);

    /**
     * 获得项目采购单申请列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 项目采购单申请列表
     */
    List<Procurement> getProcurementList(ProcurementExportReqVO exportReqVO);

}
