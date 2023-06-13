package cn.iocoder.yudao.module.jl.service.project;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.ProjectFeedback;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 项目反馈 Service 接口
 *
 */
public interface ProjectFeedbackService {

    /**
     * 创建项目反馈
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProjectFeedback(@Valid ProjectFeedbackCreateReqVO createReqVO);

    /**
     * 更新项目反馈
     *
     * @param updateReqVO 更新信息
     */
    void updateProjectFeedback(@Valid ProjectFeedbackUpdateReqVO updateReqVO);

    /**
     * 删除项目反馈
     *
     * @param id 编号
     */
    void deleteProjectFeedback(Long id);

    /**
     * 获得项目反馈
     *
     * @param id 编号
     * @return 项目反馈
     */
    Optional<ProjectFeedback> getProjectFeedback(Long id);

    /**
     * 获得项目反馈列表
     *
     * @param ids 编号
     * @return 项目反馈列表
     */
    List<ProjectFeedback> getProjectFeedbackList(Collection<Long> ids);

    /**
     * 获得项目反馈分页
     *
     * @param pageReqVO 分页查询
     * @return 项目反馈分页
     */
    PageResult<ProjectFeedback> getProjectFeedbackPage(ProjectFeedbackPageReqVO pageReqVO, ProjectFeedbackPageOrder orderV0);

    /**
     * 获得项目反馈列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 项目反馈列表
     */
    List<ProjectFeedback> getProjectFeedbackList(ProjectFeedbackExportReqVO exportReqVO);

}
