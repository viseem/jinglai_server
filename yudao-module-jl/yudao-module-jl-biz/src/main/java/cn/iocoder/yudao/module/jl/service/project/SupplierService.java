package cn.iocoder.yudao.module.jl.service.project;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.Supplier;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.system.controller.admin.user.vo.user.UserImportExcelVO;
import cn.iocoder.yudao.module.system.controller.admin.user.vo.user.UserImportRespVO;

/**
 * 项目采购单物流信息 Service 接口
 *
 */
public interface SupplierService {

    /**
     * 创建项目采购单物流信息
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSupplier(@Valid SupplierCreateReqVO createReqVO);

    /**
     * 更新项目采购单物流信息
     *
     * @param updateReqVO 更新信息
     */
    void updateSupplier(@Valid SupplierUpdateReqVO updateReqVO);

    /**
     * 删除项目采购单物流信息
     *
     * @param id 编号
     */
    void deleteSupplier(Long id);

    /**
     * 获得项目采购单物流信息
     *
     * @param id 编号
     * @return 项目采购单物流信息
     */
    Optional<Supplier> getSupplier(Long id);

    /**
     * 获得项目采购单物流信息
     *
     * @param id 编号
     * @return 项目采购单物流信息
     */
    SupplierStatsRespVO getSupplierStats(Long id);

    /**
     * 获得项目采购单物流信息列表
     *
     * @param ids 编号
     * @return 项目采购单物流信息列表
     */
    List<Supplier> getSupplierList(Collection<Long> ids);

    /**
     * 获得项目采购单物流信息分页
     *
     * @param pageReqVO 分页查询
     * @return 项目采购单物流信息分页
     */
    PageResult<Supplier> getSupplierPage(SupplierPageReqVO pageReqVO, SupplierPageOrder orderV0);

    /**
     * 获得项目采购单物流信息列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 项目采购单物流信息列表
     */
    List<Supplier> getSupplierList(SupplierExportReqVO exportReqVO);

    SupplierImportRespVO importList(List<SupplierImportVO> importUsers, boolean isUpdateSupport);
}
