package cn.iocoder.yudao.module.jl.service.project;

import java.util.*;
import javax.validation.*;

import cn.iocoder.yudao.module.jl.controller.admin.crm.vo.appcustomer.CustomerProjectPageReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.Project;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;

/**
 * 项目管理 Service 接口
 *
 */
public interface ProjectService {

    /**
     * 创建项目管理
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProject(@Valid ProjectCreateReqVO createReqVO);

    /**
     * 更新项目管理
     *
     * @param updateReqVO 更新信息
     */
    void updateProject(@Valid ProjectUpdateReqVO updateReqVO);


    /**
     * 更新项目的标签
     *
     * @param updateReqVO 更新信息
     */
    void updateProjectTag(@Valid ProjectUpdateTagReqVO updateReqVO);


    void projectOutboundApply(@Valid ProjectOutboundApplyReqVO updateReqVO);

    /**
     * 设置项目的主安排单
     */
    void setProjectCurrentSchedule(Long projectId, Long scheduleId);

    /**
     * 删除项目管理
     *
     * @param id 编号
     */
    void deleteProject(Long id);

    /**
     * 获得项目管理
     *
     * @param id 编号
     * @return 项目管理
     */
    Optional<Project> getProject(Long id);

    /**
     * 获得项目管理列表
     *
     * @param ids 编号
     * @return 项目管理列表
     */
    List<Project> getProjectList(Collection<Long> ids);

    /**
     * 获得项目管理分页
     *
     * @param pageReqVO 分页查询
     * @return 项目管理分页
     */
    PageResult<Project> getProjectPage(ProjectPageReqVO pageReqVO, ProjectPageOrder orderV0);

    ProjectSupplyAndChargeRespVO getProjectSupplyAndCharge(ProjectSupplyAndChargeReqVO reqVO);


    /**
     * 获得项目管理分页
     *
     * @param pageReqVO 分页查询
     * @return 项目管理分页
     */
    PageResult<ProjectSimple> getCustomerProjectPage(CustomerProjectPageReqVO pageReqVO, ProjectPageOrder orderV0);

    /**
     * 获得项目管理分页simple
     *
     * @param pageReqVO 分页查询
     * @return 项目管理分页
     */
    PageResult<ProjectSimple> getProjectSimplePage(ProjectPageReqVO pageReqVO, ProjectPageOrder orderV0);

    /**
     * 获得项目数字统计
     *
     * @param pageReqVO 分页查询
     * @return 项目管理分页
     */
    ProjectStatsRespVO getProjectStats(ProjectPageReqVO pageReqVO);

    /**
     * 获得项目管理列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 项目管理列表
     */
    List<Project> getProjectList(ProjectExportReqVO exportReqVO);


    /**
     * 项目转公海池 或 领取
     *
     * @param updateReqVO 更新信息
     */
    void projectToSeasOrReceive(@Valid ProjectSeasVO updateReqVO);


}
