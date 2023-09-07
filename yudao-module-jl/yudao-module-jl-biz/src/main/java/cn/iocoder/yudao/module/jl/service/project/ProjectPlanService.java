package cn.iocoder.yudao.module.jl.service.project;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.ProjectPlan;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 项目实验方案 Service 接口
 *
 */
public interface ProjectPlanService {

    /**
     * 创建项目实验方案
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProjectPlan(@Valid ProjectPlanCreateReqVO createReqVO);

    /**
     * 更新项目实验方案
     *
     * @param updateReqVO 更新信息
     */
    void updateProjectPlan(@Valid ProjectPlanUpdateReqVO updateReqVO);

    /**
     * 删除项目实验方案
     *
     * @param id 编号
     */
    void deleteProjectPlan(Long id);

    /**
     * 获得项目实验方案
     *
     * @param id 编号
     * @return 项目实验方案
     */
    Optional<ProjectPlan> getProjectPlan(Long id);

    /**
     * 获得项目实验方案列表
     *
     * @param ids 编号
     * @return 项目实验方案列表
     */
    List<ProjectPlan> getProjectPlanList(Collection<Long> ids);

    /**
     * 获得项目实验方案分页
     *
     * @param pageReqVO 分页查询
     * @return 项目实验方案分页
     */
    PageResult<ProjectPlan> getProjectPlanPage(ProjectPlanPageReqVO pageReqVO, ProjectPlanPageOrder orderV0);

    /**
     * 获得项目实验方案列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 项目实验方案列表
     */
    List<ProjectPlan> getProjectPlanList(ProjectPlanExportReqVO exportReqVO);

}
