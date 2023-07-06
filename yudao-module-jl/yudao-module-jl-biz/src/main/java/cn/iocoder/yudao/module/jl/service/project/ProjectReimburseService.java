package cn.iocoder.yudao.module.jl.service.project;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.ProjectReimburse;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 项目报销 Service 接口
 *
 */
public interface ProjectReimburseService {

    /**
     * 创建项目报销
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProjectReimburse(@Valid ProjectReimburseCreateReqVO createReqVO);

    /**
     * 更新项目报销
     *
     * @param updateReqVO 更新信息
     */
    void updateProjectReimburse(@Valid ProjectReimburseUpdateReqVO updateReqVO);

    /**
     * 删除项目报销
     *
     * @param id 编号
     */
    void deleteProjectReimburse(Long id);

    /**
     * 获得项目报销
     *
     * @param id 编号
     * @return 项目报销
     */
    Optional<ProjectReimburse> getProjectReimburse(Long id);

    /**
     * 获得项目报销列表
     *
     * @param ids 编号
     * @return 项目报销列表
     */
    List<ProjectReimburse> getProjectReimburseList(Collection<Long> ids);

    /**
     * 获得项目报销分页
     *
     * @param pageReqVO 分页查询
     * @return 项目报销分页
     */
    PageResult<ProjectReimburse> getProjectReimbursePage(ProjectReimbursePageReqVO pageReqVO, ProjectReimbursePageOrder orderV0);

    /**
     * 获得项目报销列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 项目报销列表
     */
    List<ProjectReimburse> getProjectReimburseList(ProjectReimburseExportReqVO exportReqVO);

}
