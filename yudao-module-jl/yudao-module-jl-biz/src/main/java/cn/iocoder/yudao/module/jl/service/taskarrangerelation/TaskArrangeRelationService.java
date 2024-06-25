package cn.iocoder.yudao.module.jl.service.taskarrangerelation;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.taskarrangerelation.vo.*;
import cn.iocoder.yudao.module.jl.entity.taskarrangerelation.TaskArrangeRelation;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 任务安排关系 Service 接口
 *
 */
public interface TaskArrangeRelationService {

    /**
     * 创建任务安排关系
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTaskArrangeRelation(@Valid TaskArrangeRelationCreateReqVO createReqVO);

    /**
     * 更新任务安排关系
     *
     * @param updateReqVO 更新信息
     */
    void updateTaskArrangeRelation(@Valid TaskArrangeRelationUpdateReqVO updateReqVO);

    /**
     * 删除任务安排关系
     *
     * @param id 编号
     */
    void deleteTaskArrangeRelation(Long id);

    /**
     * 获得任务安排关系
     *
     * @param id 编号
     * @return 任务安排关系
     */
    Optional<TaskArrangeRelation> getTaskArrangeRelation(Long id);

    /**
     * 获得任务安排关系列表
     *
     * @param ids 编号
     * @return 任务安排关系列表
     */
    List<TaskArrangeRelation> getTaskArrangeRelationList(Collection<Long> ids);

    /**
     * 获得任务安排关系分页
     *
     * @param pageReqVO 分页查询
     * @return 任务安排关系分页
     */
    PageResult<TaskArrangeRelation> getTaskArrangeRelationPage(TaskArrangeRelationPageReqVO pageReqVO, TaskArrangeRelationPageOrder orderV0);

    /**
     * 获得任务安排关系列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 任务安排关系列表
     */
    List<TaskArrangeRelation> getTaskArrangeRelationList(TaskArrangeRelationExportReqVO exportReqVO);

}
