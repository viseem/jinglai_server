package cn.iocoder.yudao.module.jl.service.projectcategory;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryApproval;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 项目实验名目的状态变更审批 Service 接口
 *
 */
public interface ProjectCategoryApprovalService {

    /**
     * 创建项目实验名目的状态变更审批
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProjectCategoryApproval(@Valid ProjectCategoryApprovalCreateReqVO createReqVO);

    /**
     * 更新项目实验名目的状态变更审批
     *
     * @param updateReqVO 更新信息
     */
    void updateProjectCategoryApproval(@Valid ProjectCategoryApprovalUpdateReqVO updateReqVO);

    /**
     * save更新项目实验名目的状态变更审批
     *
     * @param saveReqVO 更新信息
     */
    Long saveProjectCategoryApproval(@Valid ProjectCategoryApprovalSaveReqVO saveReqVO);

    /**
     * 删除项目实验名目的状态变更审批
     *
     * @param id 编号
     */
    void deleteProjectCategoryApproval(Long id);

    /**
     * 获得项目实验名目的状态变更审批
     *
     * @param id 编号
     * @return 项目实验名目的状态变更审批
     */
    Optional<ProjectCategoryApproval> getProjectCategoryApproval(Long id);

    /**
     * 获得项目实验名目的状态变更审批列表
     *
     * @param ids 编号
     * @return 项目实验名目的状态变更审批列表
     */
    List<ProjectCategoryApproval> getProjectCategoryApprovalList(Collection<Long> ids);

    /**
     * 获得项目实验名目的状态变更审批分页
     *
     * @param pageReqVO 分页查询
     * @return 项目实验名目的状态变更审批分页
     */
    PageResult<ProjectCategoryApproval> getProjectCategoryApprovalPage(ProjectCategoryApprovalPageReqVO pageReqVO, ProjectCategoryApprovalPageOrder orderV0);

    /**
     * 获得项目实验名目的状态变更审批列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 项目实验名目的状态变更审批列表
     */
    List<ProjectCategoryApproval> getProjectCategoryApprovalList(ProjectCategoryApprovalExportReqVO exportReqVO);

}
