package cn.iocoder.yudao.module.jl.service.commontask;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.commontask.vo.*;
import cn.iocoder.yudao.module.jl.entity.commontask.CommonTask;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 通用任务 Service 接口
 *
 */
public interface CommonTaskService {

    /**
     * 创建通用任务
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCommonTask(@Valid CommonTaskCreateReqVO createReqVO);

    /**
     * 更新通用任务
     *
     * @param updateReqVO 更新信息
     */
    void updateCommonTask(@Valid CommonTaskUpdateReqVO updateReqVO);

    /**
     * 删除通用任务
     *
     * @param id 编号
     */
    void deleteCommonTask(Long id);

    /**
     * 获得通用任务
     *
     * @param id 编号
     * @return 通用任务
     */
    Optional<CommonTask> getCommonTask(Long id);

    /**
     * 获得通用任务列表
     *
     * @param ids 编号
     * @return 通用任务列表
     */
    List<CommonTask> getCommonTaskList(Collection<Long> ids);

    /**
     * 获得通用任务分页
     *
     * @param pageReqVO 分页查询
     * @return 通用任务分页
     */
    PageResult<CommonTask> getCommonTaskPage(CommonTaskPageReqVO pageReqVO, CommonTaskPageOrder orderV0);

    /**
     * 获得通用任务列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 通用任务列表
     */
    List<CommonTask> getCommonTaskList(CommonTaskExportReqVO exportReqVO);

}
