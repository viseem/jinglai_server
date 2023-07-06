package cn.iocoder.yudao.module.jl.service.projectcategory;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategorySupplier;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 项目实验委外供应商 Service 接口
 *
 */
public interface ProjectCategorySupplierService {

    /**
     * 创建项目实验委外供应商
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProjectCategorySupplier(@Valid ProjectCategorySupplierCreateReqVO createReqVO);

    /**
     * 更新项目实验委外供应商
     *
     * @param updateReqVO 更新信息
     */
    void updateProjectCategorySupplier(@Valid ProjectCategorySupplierUpdateReqVO updateReqVO);

    /**
     * 删除项目实验委外供应商
     *
     * @param id 编号
     */
    void deleteProjectCategorySupplier(Long id);

    /**
     * 获得项目实验委外供应商
     *
     * @param id 编号
     * @return 项目实验委外供应商
     */
    Optional<ProjectCategorySupplier> getProjectCategorySupplier(Long id);

    /**
     * 获得项目实验委外供应商列表
     *
     * @param ids 编号
     * @return 项目实验委外供应商列表
     */
    List<ProjectCategorySupplier> getProjectCategorySupplierList(Collection<Long> ids);

    /**
     * 获得项目实验委外供应商分页
     *
     * @param pageReqVO 分页查询
     * @return 项目实验委外供应商分页
     */
    PageResult<ProjectCategorySupplier> getProjectCategorySupplierPage(ProjectCategorySupplierPageReqVO pageReqVO, ProjectCategorySupplierPageOrder orderV0);

    /**
     * 获得项目实验委外供应商列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 项目实验委外供应商列表
     */
    List<ProjectCategorySupplier> getProjectCategorySupplierList(ProjectCategorySupplierExportReqVO exportReqVO);

}
