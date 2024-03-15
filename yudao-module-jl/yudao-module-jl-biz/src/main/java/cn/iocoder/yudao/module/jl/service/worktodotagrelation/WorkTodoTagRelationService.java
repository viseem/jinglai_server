package cn.iocoder.yudao.module.jl.service.worktodotagrelation;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.worktodotagrelation.vo.*;
import cn.iocoder.yudao.module.jl.entity.worktodotagrelation.WorkTodoTagRelation;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 工作任务 TODO 与标签的关联 Service 接口
 *
 */
public interface WorkTodoTagRelationService {

    /**
     * 创建工作任务 TODO 与标签的关联
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createWorkTodoTagRelation(@Valid WorkTodoTagRelationCreateReqVO createReqVO);

    /**
     * 更新工作任务 TODO 与标签的关联
     *
     * @param updateReqVO 更新信息
     */
    void updateWorkTodoTagRelation(@Valid WorkTodoTagRelationUpdateReqVO updateReqVO);

    /**
     * 删除工作任务 TODO 与标签的关联
     *
     * @param id 编号
     */
    void deleteWorkTodoTagRelation(Long id);

    /**
     * 获得工作任务 TODO 与标签的关联
     *
     * @param id 编号
     * @return 工作任务 TODO 与标签的关联
     */
    Optional<WorkTodoTagRelation> getWorkTodoTagRelation(Long id);

    /**
     * 获得工作任务 TODO 与标签的关联列表
     *
     * @param ids 编号
     * @return 工作任务 TODO 与标签的关联列表
     */
    List<WorkTodoTagRelation> getWorkTodoTagRelationList(Collection<Long> ids);

    /**
     * 获得工作任务 TODO 与标签的关联分页
     *
     * @param pageReqVO 分页查询
     * @return 工作任务 TODO 与标签的关联分页
     */
    PageResult<WorkTodoTagRelation> getWorkTodoTagRelationPage(WorkTodoTagRelationPageReqVO pageReqVO, WorkTodoTagRelationPageOrder orderV0);

    /**
     * 获得工作任务 TODO 与标签的关联列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 工作任务 TODO 与标签的关联列表
     */
    List<WorkTodoTagRelation> getWorkTodoTagRelationList(WorkTodoTagRelationExportReqVO exportReqVO);

}
