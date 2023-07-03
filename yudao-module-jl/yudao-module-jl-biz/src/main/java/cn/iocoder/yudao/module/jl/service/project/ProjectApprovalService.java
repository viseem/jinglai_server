package cn.iocoder.yudao.module.jl.service.project;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.ProjectApproval;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 项目的状态变更记录 Service 接口
 *
 */
public interface ProjectApprovalService {

    /**
     * 创建项目的状态变更记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProjectApproval(@Valid ProjectApprovalCreateReqVO createReqVO);

    /**
     * 更新项目的状态变更记录
     *
     * @param updateReqVO 更新信息
     */
    void updateProjectApproval(@Valid ProjectApprovalUpdateReqVO updateReqVO);

    /**
     * 删除项目的状态变更记录
     *
     * @param id 编号
     */
    void deleteProjectApproval(Long id);

    /**
     * 获得项目的状态变更记录
     *
     * @param id 编号
     * @return 项目的状态变更记录
     */
    Optional<ProjectApproval> getProjectApproval(Long id);

    /**
     * 获得项目的状态变更记录列表
     *
     * @param ids 编号
     * @return 项目的状态变更记录列表
     */
    List<ProjectApproval> getProjectApprovalList(Collection<Long> ids);

    /**
     * 获得项目的状态变更记录分页
     *
     * @param pageReqVO 分页查询
     * @return 项目的状态变更记录分页
     */
    PageResult<ProjectApproval> getProjectApprovalPage(ProjectApprovalPageReqVO pageReqVO, ProjectApprovalPageOrder orderV0);

    /**
     * 获得项目的状态变更记录列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 项目的状态变更记录列表
     */
    List<ProjectApproval> getProjectApprovalList(ProjectApprovalExportReqVO exportReqVO);

}
