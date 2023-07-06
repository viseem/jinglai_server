package cn.iocoder.yudao.module.jl.service.projectcategory;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryOutsource;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 项目实验委外 Service 接口
 *
 */
public interface ProjectCategoryOutsourceService {

    /**
     * 创建项目实验委外
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProjectCategoryOutsource(@Valid ProjectCategoryOutsourceCreateReqVO createReqVO);

    /**
     * 更新项目实验委外
     *
     * @param updateReqVO 更新信息
     */
    void updateProjectCategoryOutsource(@Valid ProjectCategoryOutsourceUpdateReqVO updateReqVO);

    /**
     * 删除项目实验委外
     *
     * @param id 编号
     */
    void deleteProjectCategoryOutsource(Long id);

    /**
     * 获得项目实验委外
     *
     * @param id 编号
     * @return 项目实验委外
     */
    Optional<ProjectCategoryOutsource> getProjectCategoryOutsource(Long id);

    /**
     * 获得项目实验委外列表
     *
     * @param ids 编号
     * @return 项目实验委外列表
     */
    List<ProjectCategoryOutsource> getProjectCategoryOutsourceList(Collection<Long> ids);

    /**
     * 获得项目实验委外分页
     *
     * @param pageReqVO 分页查询
     * @return 项目实验委外分页
     */
    PageResult<ProjectCategoryOutsource> getProjectCategoryOutsourcePage(ProjectCategoryOutsourcePageReqVO pageReqVO, ProjectCategoryOutsourcePageOrder orderV0);

    /**
     * 获得项目实验委外列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 项目实验委外列表
     */
    List<ProjectCategoryOutsource> getProjectCategoryOutsourceList(ProjectCategoryOutsourceExportReqVO exportReqVO);

}
