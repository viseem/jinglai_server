package cn.iocoder.yudao.module.jl.service.commontodo;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.commontodo.vo.*;
import cn.iocoder.yudao.module.jl.entity.commontodo.CommonTodo;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 通用TODO Service 接口
 *
 */
public interface CommonTodoService {

    /**
     * 创建通用TODO
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCommonTodo(@Valid CommonTodoCreateReqVO createReqVO);

    /**
     * 更新通用TODO
     *
     * @param updateReqVO 更新信息
     */
    void updateCommonTodo(@Valid CommonTodoUpdateReqVO updateReqVO);

    /**
     * 删除通用TODO
     *
     * @param id 编号
     */
    void deleteCommonTodo(Long id);

    /**
     * 获得通用TODO
     *
     * @param id 编号
     * @return 通用TODO
     */
    Optional<CommonTodo> getCommonTodo(Long id);

    /**
     * 获得通用TODO列表
     *
     * @param ids 编号
     * @return 通用TODO列表
     */
    List<CommonTodo> getCommonTodoList(Collection<Long> ids);

    /**
     * 获得通用TODO分页
     *
     * @param pageReqVO 分页查询
     * @return 通用TODO分页
     */
    PageResult<CommonTodo> getCommonTodoPage(CommonTodoPageReqVO pageReqVO, CommonTodoPageOrder orderV0);

    /**
     * 获得通用TODO列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 通用TODO列表
     */
    List<CommonTodo> getCommonTodoList(CommonTodoExportReqVO exportReqVO);

}
