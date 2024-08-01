package cn.iocoder.yudao.module.jl.service.projectoutlog;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.projectoutlog.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectoutlog.ProjectOutLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * example 空 Service 接口
 *
 */
public interface ProjectOutLogService {

    /**
     * 创建example 空
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProjectOutLog(@Valid ProjectOutLogCreateReqVO createReqVO);

    /**
     * 更新example 空
     *
     * @param updateReqVO 更新信息
     */
    void updateProjectOutLog(@Valid ProjectOutLogUpdateReqVO updateReqVO);

    /**
     * 更新example 空
     *
     * @param updateReqVO 更新信息
     */
    void updateProjectOutLogDataStep(@Valid ProjectOutLogUpdateReqVO updateReqVO);

    /**
     * 更新example 空
     *
     * @param updateReqVO 更新信息
     */
    void updateProjectOutLogCustomerStep(@Valid ProjectOutLogUpdateReqVO updateReqVO);

    /**
     * 删除example 空
     *
     * @param id 编号
     */
    void deleteProjectOutLog(Long id);

    /**
     * 获得example 空
     *
     * @param id 编号
     * @return example 空
     */
    Optional<ProjectOutLog> getProjectOutLog(Long id);

    /**
     * 获得example 空列表
     *
     * @param ids 编号
     * @return example 空列表
     */
    List<ProjectOutLog> getProjectOutLogList(Collection<Long> ids);

    /**
     * 获得example 空分页
     *
     * @param pageReqVO 分页查询
     * @return example 空分页
     */
    PageResult<ProjectOutLog> getProjectOutLogPage(ProjectOutLogPageReqVO pageReqVO, ProjectOutLogPageOrder orderV0);

    /**
     * 获得example 空列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return example 空列表
     */
    List<ProjectOutLog> getProjectOutLogList(ProjectOutLogExportReqVO exportReqVO);

}
