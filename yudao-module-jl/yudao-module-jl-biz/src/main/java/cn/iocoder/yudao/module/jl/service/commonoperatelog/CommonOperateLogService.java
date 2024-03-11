package cn.iocoder.yudao.module.jl.service.commonoperatelog;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.commonoperatelog.vo.*;
import cn.iocoder.yudao.module.jl.entity.commonoperatelog.CommonOperateLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 通用操作记录 Service 接口
 *
 */
public interface CommonOperateLogService {

    /**
     * 创建通用操作记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCommonOperateLog(@Valid CommonOperateLogCreateReqVO createReqVO);

    /**
     * 更新通用操作记录
     *
     * @param updateReqVO 更新信息
     */
    void updateCommonOperateLog(@Valid CommonOperateLogUpdateReqVO updateReqVO);

    /**
     * 删除通用操作记录
     *
     * @param id 编号
     */
    void deleteCommonOperateLog(Long id);

    /**
     * 获得通用操作记录
     *
     * @param id 编号
     * @return 通用操作记录
     */
    Optional<CommonOperateLog> getCommonOperateLog(Long id);

    /**
     * 获得通用操作记录列表
     *
     * @param ids 编号
     * @return 通用操作记录列表
     */
    List<CommonOperateLog> getCommonOperateLogList(Collection<Long> ids);

    /**
     * 获得通用操作记录分页
     *
     * @param pageReqVO 分页查询
     * @return 通用操作记录分页
     */
    PageResult<CommonOperateLog> getCommonOperateLogPage(CommonOperateLogPageReqVO pageReqVO, CommonOperateLogPageOrder orderV0);

    /**
     * 获得通用操作记录列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 通用操作记录列表
     */
    List<CommonOperateLog> getCommonOperateLogList(CommonOperateLogExportReqVO exportReqVO);

}
