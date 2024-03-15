package cn.iocoder.yudao.module.jl.service.worktodotag;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.worktodotag.vo.*;
import cn.iocoder.yudao.module.jl.entity.worktodotag.WorkTodoTag;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 工作任务 TODO 的标签 Service 接口
 *
 */
public interface WorkTodoTagService {

    /**
     * 创建工作任务 TODO 的标签
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createWorkTodoTag(@Valid WorkTodoTagCreateReqVO createReqVO);

    /**
     * 更新工作任务 TODO 的标签
     *
     * @param updateReqVO 更新信息
     */
    void updateWorkTodoTag(@Valid WorkTodoTagUpdateReqVO updateReqVO);

    /**
     * 删除工作任务 TODO 的标签
     *
     * @param id 编号
     */
    void deleteWorkTodoTag(Long id);

    /**
     * 获得工作任务 TODO 的标签
     *
     * @param id 编号
     * @return 工作任务 TODO 的标签
     */
    Optional<WorkTodoTag> getWorkTodoTag(Long id);

    /**
     * 获得工作任务 TODO 的标签列表
     *
     * @param ids 编号
     * @return 工作任务 TODO 的标签列表
     */
    List<WorkTodoTag> getWorkTodoTagList(Collection<Long> ids);

    /**
     * 获得工作任务 TODO 的标签分页
     *
     * @param pageReqVO 分页查询
     * @return 工作任务 TODO 的标签分页
     */
    PageResult<WorkTodoTag> getWorkTodoTagPage(WorkTodoTagPageReqVO pageReqVO, WorkTodoTagPageOrder orderV0);

    /**
     * 获得工作任务 TODO 的标签列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 工作任务 TODO 的标签列表
     */
    List<WorkTodoTag> getWorkTodoTagList(WorkTodoTagExportReqVO exportReqVO);

}
