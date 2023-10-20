package cn.iocoder.yudao.module.jl.service.projectfundchangelog;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.projectfundchangelog.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectfundchangelog.ProjectFundChangeLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 款项计划变更日志 Service 接口
 *
 */
public interface ProjectFundChangeLogService {

    /**
     * 创建款项计划变更日志
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProjectFundChangeLog(@Valid ProjectFundChangeLogCreateReqVO createReqVO);

    /**
     * 更新款项计划变更日志
     *
     * @param updateReqVO 更新信息
     */
    void updateProjectFundChangeLog(@Valid ProjectFundChangeLogUpdateReqVO updateReqVO);

    /**
     * 删除款项计划变更日志
     *
     * @param id 编号
     */
    void deleteProjectFundChangeLog(Long id);

    /**
     * 获得款项计划变更日志
     *
     * @param id 编号
     * @return 款项计划变更日志
     */
    Optional<ProjectFundChangeLog> getProjectFundChangeLog(Long id);

    /**
     * 获得款项计划变更日志列表
     *
     * @param ids 编号
     * @return 款项计划变更日志列表
     */
    List<ProjectFundChangeLog> getProjectFundChangeLogList(Collection<Long> ids);

    /**
     * 获得款项计划变更日志分页
     *
     * @param pageReqVO 分页查询
     * @return 款项计划变更日志分页
     */
    PageResult<ProjectFundChangeLog> getProjectFundChangeLogPage(ProjectFundChangeLogPageReqVO pageReqVO, ProjectFundChangeLogPageOrder orderV0);

    /**
     * 获得款项计划变更日志列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 款项计划变更日志列表
     */
    List<ProjectFundChangeLog> getProjectFundChangeLogList(ProjectFundChangeLogExportReqVO exportReqVO);

}
