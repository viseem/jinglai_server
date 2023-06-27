package cn.iocoder.yudao.module.jl.service.projectcategory;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 实验名目的操作记录 Service 接口
 *
 */
public interface ProjectCategoryLogService {

    /**
     * 创建实验名目的操作记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProjectCategoryLog(@Valid ProjectCategoryLogCreateReqVO createReqVO);

    /**
     * 更新实验名目的操作记录
     *
     * @param updateReqVO 更新信息
     */
    void updateProjectCategoryLog(@Valid ProjectCategoryLogUpdateReqVO updateReqVO);

    /**
     * 删除实验名目的操作记录
     *
     * @param id 编号
     */
    void deleteProjectCategoryLog(Long id);

    /**
     * 获得实验名目的操作记录
     *
     * @param id 编号
     * @return 实验名目的操作记录
     */
    Optional<ProjectCategoryLog> getProjectCategoryLog(Long id);

    /**
     * 获得实验名目的操作记录列表
     *
     * @param ids 编号
     * @return 实验名目的操作记录列表
     */
    List<ProjectCategoryLog> getProjectCategoryLogList(Collection<Long> ids);

    /**
     * 获得实验名目的操作记录分页
     *
     * @param pageReqVO 分页查询
     * @return 实验名目的操作记录分页
     */
    PageResult<ProjectCategoryLog> getProjectCategoryLogPage(ProjectCategoryLogPageReqVO pageReqVO, ProjectCategoryLogPageOrder orderV0);

    /**
     * 获得实验名目的操作记录列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 实验名目的操作记录列表
     */
    List<ProjectCategoryLog> getProjectCategoryLogList(ProjectCategoryLogExportReqVO exportReqVO);

}
