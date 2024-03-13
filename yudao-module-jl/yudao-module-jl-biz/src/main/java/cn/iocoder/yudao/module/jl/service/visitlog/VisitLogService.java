package cn.iocoder.yudao.module.jl.service.visitlog;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.visitlog.vo.*;
import cn.iocoder.yudao.module.jl.entity.visitlog.VisitLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 拜访记录 Service 接口
 *
 */
public interface VisitLogService {

    /**
     * 创建拜访记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createVisitLog(@Valid VisitLogCreateReqVO createReqVO);

    /**
     * 更新拜访记录
     *
     * @param updateReqVO 更新信息
     */
    void updateVisitLog(@Valid VisitLogUpdateReqVO updateReqVO);

    /**
     * 删除拜访记录
     *
     * @param id 编号
     */
    void deleteVisitLog(Long id);

    /**
     * 获得拜访记录
     *
     * @param id 编号
     * @return 拜访记录
     */
    Optional<VisitLog> getVisitLog(Long id);

    /**
     * 获得拜访记录列表
     *
     * @param ids 编号
     * @return 拜访记录列表
     */
    List<VisitLog> getVisitLogList(Collection<Long> ids);

    /**
     * 获得拜访记录分页
     *
     * @param pageReqVO 分页查询
     * @return 拜访记录分页
     */
    PageResult<VisitLog> getVisitLogPage(VisitLogPageReqVO pageReqVO, VisitLogPageOrder orderV0);

    /**
     * 获得拜访记录列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 拜访记录列表
     */
    List<VisitLog> getVisitLogList(VisitLogExportReqVO exportReqVO);

}
