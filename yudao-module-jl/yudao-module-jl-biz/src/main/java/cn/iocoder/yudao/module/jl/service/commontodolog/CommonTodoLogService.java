package cn.iocoder.yudao.module.jl.service.commontodolog;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.commontodolog.vo.*;
import cn.iocoder.yudao.module.jl.entity.commontodolog.CommonTodoLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 通用TODO记录 Service 接口
 *
 */
public interface CommonTodoLogService {

    /**
     * 创建通用TODO记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCommonTodoLog(@Valid CommonTodoLogCreateReqVO createReqVO);

    /**
     * 更新通用TODO记录
     *
     * @param updateReqVO 更新信息
     */
    void updateCommonTodoLog(@Valid CommonTodoLogUpdateReqVO updateReqVO);

    /**
     * 删除通用TODO记录
     *
     * @param id 编号
     */
    void deleteCommonTodoLog(Long id);

    /**
     * 获得通用TODO记录
     *
     * @param id 编号
     * @return 通用TODO记录
     */
    Optional<CommonTodoLog> getCommonTodoLog(Long id);

    /**
     * 获得通用TODO记录列表
     *
     * @param ids 编号
     * @return 通用TODO记录列表
     */
    List<CommonTodoLog> getCommonTodoLogList(Collection<Long> ids);

    /**
     * 获得通用TODO记录分页
     *
     * @param pageReqVO 分页查询
     * @return 通用TODO记录分页
     */
    PageResult<CommonTodoLog> getCommonTodoLogPage(CommonTodoLogPageReqVO pageReqVO, CommonTodoLogPageOrder orderV0);

    /**
     * 获得通用TODO记录列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 通用TODO记录列表
     */
    List<CommonTodoLog> getCommonTodoLogList(CommonTodoLogExportReqVO exportReqVO);

}
