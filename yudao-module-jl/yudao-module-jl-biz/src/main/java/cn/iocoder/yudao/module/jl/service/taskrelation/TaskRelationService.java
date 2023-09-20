package cn.iocoder.yudao.module.jl.service.taskrelation;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.taskrelation.vo.*;
import cn.iocoder.yudao.module.jl.entity.taskrelation.TaskRelation;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 任务关系依赖 Service 接口
 *
 */
public interface TaskRelationService {

    /**
     * 创建任务关系依赖
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTaskRelation(@Valid TaskRelationCreateReqVO createReqVO);

    /**
     * 更新任务关系依赖
     *
     * @param updateReqVO 更新信息
     */
    void updateTaskRelation(@Valid TaskRelationUpdateReqVO updateReqVO);

    /**
     * 删除任务关系依赖
     *
     * @param id 编号
     */
    void deleteTaskRelation(Long id);

    /**
     * 获得任务关系依赖
     *
     * @param id 编号
     * @return 任务关系依赖
     */
    Optional<TaskRelation> getTaskRelation(Long id);

    /**
     * 获得任务关系依赖列表
     *
     * @param ids 编号
     * @return 任务关系依赖列表
     */
    List<TaskRelation> getTaskRelationList(Collection<Long> ids);

    /**
     * 获得任务关系依赖分页
     *
     * @param pageReqVO 分页查询
     * @return 任务关系依赖分页
     */
    PageResult<TaskRelation> getTaskRelationPage(TaskRelationPageReqVO pageReqVO, TaskRelationPageOrder orderV0);

    /**
     * 获得任务关系依赖列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 任务关系依赖列表
     */
    List<TaskRelation> getTaskRelationList(TaskRelationExportReqVO exportReqVO);

}
