package cn.iocoder.yudao.module.jl.service.worktodo;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.worktodo.vo.*;
import cn.iocoder.yudao.module.jl.entity.worktodo.WorkTodo;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 工作任务 TODO Service 接口
 *
 */
public interface WorkTodoService {

    /**
     * 创建工作任务 TODO
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createWorkTodo(@Valid WorkTodoCreateReqVO createReqVO);

    /**
     * 更新工作任务 TODO
     *
     * @param updateReqVO 更新信息
     */
    void updateWorkTodo(@Valid WorkTodoUpdateReqVO updateReqVO);

    /**
     * 删除工作任务 TODO
     *
     * @param id 编号
     */
    void deleteWorkTodo(Long id);

    /**
     * 获得工作任务 TODO
     *
     * @param id 编号
     * @return 工作任务 TODO
     */
    Optional<WorkTodo> getWorkTodo(Long id);

    /**
     * 获得工作任务 TODO列表
     *
     * @param ids 编号
     * @return 工作任务 TODO列表
     */
    List<WorkTodo> getWorkTodoList(Collection<Long> ids);

    /**
     * 获得工作任务 TODO分页
     *
     * @param pageReqVO 分页查询
     * @return 工作任务 TODO分页
     */
    PageResult<WorkTodo> getWorkTodoPage(WorkTodoPageReqVO pageReqVO, WorkTodoPageOrder orderV0);

    /**
     * 获得工作任务 TODO列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 工作任务 TODO列表
     */
    List<WorkTodo> getWorkTodoList(WorkTodoExportReqVO exportReqVO);

}
