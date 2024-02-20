package cn.iocoder.yudao.module.jl.service.commonlog;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.commonlog.vo.*;
import cn.iocoder.yudao.module.jl.entity.commonlog.CommonLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 管控记录 Service 接口
 *
 */
public interface CommonLogService {

    /**
     * 创建管控记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCommonLog(@Valid CommonLogCreateReqVO createReqVO);

    /**
     * 更新管控记录
     *
     * @param updateReqVO 更新信息
     */
    void updateCommonLog(@Valid CommonLogUpdateReqVO updateReqVO);

    /**
     * 删除管控记录
     *
     * @param id 编号
     */
    void deleteCommonLog(Long id);

    /**
     * 获得管控记录
     *
     * @param id 编号
     * @return 管控记录
     */
    Optional<CommonLog> getCommonLog(Long id);

    /**
     * 获得管控记录列表
     *
     * @param ids 编号
     * @return 管控记录列表
     */
    List<CommonLog> getCommonLogList(Collection<Long> ids);

    /**
     * 获得管控记录分页
     *
     * @param pageReqVO 分页查询
     * @return 管控记录分页
     */
    PageResult<CommonLog> getCommonLogPage(CommonLogPageReqVO pageReqVO, CommonLogPageOrder orderV0);

    /**
     * 获得管控记录列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 管控记录列表
     */
    List<CommonLog> getCommonLogList(CommonLogExportReqVO exportReqVO);

}
