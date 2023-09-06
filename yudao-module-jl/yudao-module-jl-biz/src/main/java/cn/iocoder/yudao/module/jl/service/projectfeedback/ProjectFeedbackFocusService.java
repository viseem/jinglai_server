package cn.iocoder.yudao.module.jl.service.projectfeedback;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.projectfeedback.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectfeedback.ProjectFeedbackFocus;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 晶莱项目反馈关注人员 Service 接口
 *
 */
public interface ProjectFeedbackFocusService {

    /**
     * 创建晶莱项目反馈关注人员
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProjectFeedbackFocus(@Valid ProjectFeedbackFocusCreateReqVO createReqVO);

    /**
     * 更新晶莱项目反馈关注人员
     *
     * @param updateReqVO 更新信息
     */
    void updateProjectFeedbackFocus(@Valid ProjectFeedbackFocusUpdateReqVO updateReqVO);

    /**
     * 删除晶莱项目反馈关注人员
     *
     * @param id 编号
     */
    void deleteProjectFeedbackFocus(Long id);

    /**
     * 获得晶莱项目反馈关注人员
     *
     * @param id 编号
     * @return 晶莱项目反馈关注人员
     */
    Optional<ProjectFeedbackFocus> getProjectFeedbackFocus(Long id);

    /**
     * 获得晶莱项目反馈关注人员列表
     *
     * @param ids 编号
     * @return 晶莱项目反馈关注人员列表
     */
    List<ProjectFeedbackFocus> getProjectFeedbackFocusList(Collection<Long> ids);

    /**
     * 获得晶莱项目反馈关注人员分页
     *
     * @param pageReqVO 分页查询
     * @return 晶莱项目反馈关注人员分页
     */
    PageResult<ProjectFeedbackFocus> getProjectFeedbackFocusPage(ProjectFeedbackFocusPageReqVO pageReqVO, ProjectFeedbackFocusPageOrder orderV0);

    /**
     * 获得晶莱项目反馈关注人员列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 晶莱项目反馈关注人员列表
     */
    List<ProjectFeedbackFocus> getProjectFeedbackFocusList(ProjectFeedbackFocusExportReqVO exportReqVO);

}
