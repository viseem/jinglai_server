package cn.iocoder.yudao.module.jl.service.project;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.ProcurementShipment;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 项目采购单物流信息 Service 接口
 *
 */
public interface ProcurementShipmentService {

    /**
     * 创建项目采购单物流信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProcurementShipment(@Valid ProcurementShipmentCreateReqVO createReqVO);

    /**
     * 更新项目采购单物流信息
     *
     * @param updateReqVO 更新信息
     */
    void updateProcurementShipment(@Valid ProcurementShipmentUpdateReqVO updateReqVO);

    /**
     * 删除项目采购单物流信息
     *
     * @param id 编号
     */
    void deleteProcurementShipment(Long id);

    /**
     * 获得项目采购单物流信息
     *
     * @param id 编号
     * @return 项目采购单物流信息
     */
    Optional<ProcurementShipment> getProcurementShipment(Long id);

    /**
     * 获得项目采购单物流信息列表
     *
     * @param ids 编号
     * @return 项目采购单物流信息列表
     */
    List<ProcurementShipment> getProcurementShipmentList(Collection<Long> ids);

    /**
     * 获得项目采购单物流信息分页
     *
     * @param pageReqVO 分页查询
     * @return 项目采购单物流信息分页
     */
    PageResult<ProcurementShipment> getProcurementShipmentPage(ProcurementShipmentPageReqVO pageReqVO, ProcurementShipmentPageOrder orderV0);

    /**
     * 获得项目采购单物流信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 项目采购单物流信息列表
     */
    List<ProcurementShipment> getProcurementShipmentList(ProcurementShipmentExportReqVO exportReqVO);

}
