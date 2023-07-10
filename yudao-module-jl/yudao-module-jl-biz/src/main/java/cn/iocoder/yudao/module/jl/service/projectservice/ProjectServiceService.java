package cn.iocoder.yudao.module.jl.service.projectservice;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.projectservice.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectservice.ProjectService;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 项目售后 Service 接口
 *
 */
public interface ProjectServiceService {

    /**
     * 创建项目售后
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProjectService(@Valid ProjectServiceCreateReqVO createReqVO);

    /**
     * 更新项目售后
     *
     * @param updateReqVO 更新信息
     */
    void updateProjectService(@Valid ProjectServiceUpdateReqVO updateReqVO);

    /**
     * 删除项目售后
     *
     * @param id 编号
     */
    void deleteProjectService(Long id);

    /**
     * 获得项目售后
     *
     * @param id 编号
     * @return 项目售后
     */
    Optional<ProjectService> getProjectService(Long id);

    /**
     * 获得项目售后列表
     *
     * @param ids 编号
     * @return 项目售后列表
     */
    List<ProjectService> getProjectServiceList(Collection<Long> ids);

    /**
     * 获得项目售后分页
     *
     * @param pageReqVO 分页查询
     * @return 项目售后分页
     */
    PageResult<ProjectService> getProjectServicePage(ProjectServicePageReqVO pageReqVO, ProjectServicePageOrder orderV0);

    /**
     * 获得项目售后列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 项目售后列表
     */
    List<ProjectService> getProjectServiceList(ProjectServiceExportReqVO exportReqVO);

}
