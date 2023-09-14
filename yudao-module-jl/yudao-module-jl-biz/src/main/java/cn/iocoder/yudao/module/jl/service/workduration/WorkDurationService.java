package cn.iocoder.yudao.module.jl.service.workduration;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.workduration.vo.*;
import cn.iocoder.yudao.module.jl.entity.workduration.WorkDuration;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 工时 Service 接口
 *
 */
public interface WorkDurationService {

    /**
     * 创建工时
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createWorkDuration(@Valid WorkDurationCreateReqVO createReqVO);

    /**
     * 更新工时
     *
     * @param updateReqVO 更新信息
     */
    void updateWorkDuration(@Valid WorkDurationUpdateReqVO updateReqVO);

    /**
     * 删除工时
     *
     * @param id 编号
     */
    void deleteWorkDuration(Long id);

    /**
     * 获得工时
     *
     * @param id 编号
     * @return 工时
     */
    Optional<WorkDuration> getWorkDuration(Long id);

    /**
     * 获得工时列表
     *
     * @param ids 编号
     * @return 工时列表
     */
    List<WorkDuration> getWorkDurationList(Collection<Long> ids);

    /**
     * 获得工时分页
     *
     * @param pageReqVO 分页查询
     * @return 工时分页
     */
    PageResult<WorkDuration> getWorkDurationPage(WorkDurationPageReqVO pageReqVO, WorkDurationPageOrder orderV0);

    /**
     * 获得工时列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 工时列表
     */
    List<WorkDuration> getWorkDurationList(WorkDurationExportReqVO exportReqVO);

}
