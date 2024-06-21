package cn.iocoder.yudao.module.jl.service.taskproduct;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.taskproduct.vo.*;
import cn.iocoder.yudao.module.jl.entity.taskproduct.TaskProduct;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 任务产品中间 Service 接口
 *
 */
public interface TaskProductService {

    /**
     * 创建任务产品中间
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createTaskProduct(@Valid TaskProductCreateReqVO createReqVO);

    /**
     * 更新任务产品中间
     *
     * @param updateReqVO 更新信息
     */
    void updateTaskProduct(@Valid TaskProductUpdateReqVO updateReqVO);

    /**
     * 删除任务产品中间
     *
     * @param id 编号
     */
    void deleteTaskProduct(Long id);

    /**
     * 获得任务产品中间
     *
     * @param id 编号
     * @return 任务产品中间
     */
    Optional<TaskProduct> getTaskProduct(Long id);

    /**
     * 获得任务产品中间列表
     *
     * @param ids 编号
     * @return 任务产品中间列表
     */
    List<TaskProduct> getTaskProductList(Collection<Long> ids);

    /**
     * 获得任务产品中间分页
     *
     * @param pageReqVO 分页查询
     * @return 任务产品中间分页
     */
    PageResult<TaskProduct> getTaskProductPage(TaskProductPageReqVO pageReqVO, TaskProductPageOrder orderV0);

    /**
     * 获得任务产品中间列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 任务产品中间列表
     */
    List<TaskProduct> getTaskProductList(TaskProductExportReqVO exportReqVO);

}
