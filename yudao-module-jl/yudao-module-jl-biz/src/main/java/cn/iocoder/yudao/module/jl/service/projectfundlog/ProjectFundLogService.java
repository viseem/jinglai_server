package cn.iocoder.yudao.module.jl.service.projectfundlog;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.projectfundlog.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectfundlog.ProjectFundLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 项目款项 Service 接口
 *
 */
public interface ProjectFundLogService {

    /**
     * 创建项目款项
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProjectFundLog(@Valid ProjectFundLogCreateReqVO createReqVO);

    /**
     * 更新项目款项
     *
     * @param updateReqVO 更新信息
     */
    void updateProjectFundLog(@Valid ProjectFundLogUpdateReqVO updateReqVO);

    /**
     * 删除项目款项
     *
     * @param id 编号
     */
    void deleteProjectFundLog(Long id);

    /**
     * 获得项目款项
     *
     * @param id 编号
     * @return 项目款项
     */
    Optional<ProjectFundLog> getProjectFundLog(Long id);

    /**
     * 获得项目款项列表
     *
     * @param ids 编号
     * @return 项目款项列表
     */
    List<ProjectFundLog> getProjectFundLogList(Collection<Long> ids);

    /**
     * 获得项目款项分页
     *
     * @param pageReqVO 分页查询
     * @return 项目款项分页
     */
    PageResult<ProjectFundLog> getProjectFundLogPage(ProjectFundLogPageReqVO pageReqVO, ProjectFundLogPageOrder orderV0);

    /**
     * 获得项目款项列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 项目款项列表
     */
    List<ProjectFundLog> getProjectFundLogList(ProjectFundLogExportReqVO exportReqVO);

}
