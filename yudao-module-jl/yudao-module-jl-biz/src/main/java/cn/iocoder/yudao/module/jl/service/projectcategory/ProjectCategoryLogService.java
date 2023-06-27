package cn.iocoder.yudao.module.jl.service.projectcategory;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 项目实验名目的操作日志 Service 接口
 *
 */
public interface ProjectCategoryLogService {

    /**
     * 创建项目实验名目的操作日志
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProjectCategoryLog(@Valid ProjectCategoryLogCreateReqVO createReqVO);

    /**
     * 更新项目实验名目的操作日志
     *
     * @param updateReqVO 更新信息
     */
    void updateProjectCategoryLog(@Valid ProjectCategoryLogUpdateReqVO updateReqVO);

    /**
     * 删除项目实验名目的操作日志
     *
     * @param id 编号
     */
    void deleteProjectCategoryLog(Long id);

    /**
     * 获得项目实验名目的操作日志
     *
     * @param id 编号
     * @return 项目实验名目的操作日志
     */
    Optional<ProjectCategoryLog> getProjectCategoryLog(Long id);

    /**
     * 获得项目实验名目的操作日志列表
     *
     * @param ids 编号
     * @return 项目实验名目的操作日志列表
     */
    List<ProjectCategoryLog> getProjectCategoryLogList(Collection<Long> ids);

    /**
     * 获得项目实验名目的操作日志分页
     *
     * @param pageReqVO 分页查询
     * @return 项目实验名目的操作日志分页
     */
    PageResult<ProjectCategoryLog> getProjectCategoryLogPage(ProjectCategoryLogPageReqVO pageReqVO, ProjectCategoryLogPageOrder orderV0);

    /**
     * 获得项目实验名目的操作日志列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 项目实验名目的操作日志列表
     */
    List<ProjectCategoryLog> getProjectCategoryLogList(ProjectCategoryLogExportReqVO exportReqVO);

}
