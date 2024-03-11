package cn.iocoder.yudao.module.jl.service.projectpushlog;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.projectpushlog.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectpushlog.ProjectPushLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 项目推进记录 Service 接口
 *
 */
public interface ProjectPushLogService {

    /**
     * 创建项目推进记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProjectPushLog(@Valid ProjectPushLogCreateReqVO createReqVO);

    /**
     * 更新项目推进记录
     *
     * @param updateReqVO 更新信息
     */
    void updateProjectPushLog(@Valid ProjectPushLogUpdateReqVO updateReqVO);

    /**
     * 删除项目推进记录
     *
     * @param id 编号
     */
    void deleteProjectPushLog(Long id);

    /**
     * 获得项目推进记录
     *
     * @param id 编号
     * @return 项目推进记录
     */
    Optional<ProjectPushLog> getProjectPushLog(Long id);

    /**
     * 获得项目推进记录列表
     *
     * @param ids 编号
     * @return 项目推进记录列表
     */
    List<ProjectPushLog> getProjectPushLogList(Collection<Long> ids);

    /**
     * 获得项目推进记录分页
     *
     * @param pageReqVO 分页查询
     * @return 项目推进记录分页
     */
    PageResult<ProjectPushLog> getProjectPushLogPage(ProjectPushLogPageReqVO pageReqVO, ProjectPushLogPageOrder orderV0);

    /**
     * 获得项目推进记录列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 项目推进记录列表
     */
    List<ProjectPushLog> getProjectPushLogList(ProjectPushLogExportReqVO exportReqVO);

}
