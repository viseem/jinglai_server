package cn.iocoder.yudao.module.jl.service.project;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.ProjectDevice;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 项目设备 Service 接口
 *
 */
public interface ProjectDeviceService {

    /**
     * 创建项目设备
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProjectDevice(@Valid ProjectDeviceCreateReqVO createReqVO);

    /**
     * 更新项目设备
     *
     * @param updateReqVO 更新信息
     */
    void updateProjectDevice(@Valid ProjectDeviceUpdateReqVO updateReqVO);

    /**
     * 删除项目设备
     *
     * @param id 编号
     */
    void deleteProjectDevice(Long id);

    /**
     * 获得项目设备
     *
     * @param id 编号
     * @return 项目设备
     */
    Optional<ProjectDevice> getProjectDevice(Long id);

    /**
     * 获得项目设备列表
     *
     * @param ids 编号
     * @return 项目设备列表
     */
    List<ProjectDevice> getProjectDeviceList(Collection<Long> ids);

    /**
     * 获得项目设备分页
     *
     * @param pageReqVO 分页查询
     * @return 项目设备分页
     */
    PageResult<ProjectDevice> getProjectDevicePage(ProjectDevicePageReqVO pageReqVO, ProjectDevicePageOrder orderV0);

    /**
     * 获得项目设备列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 项目设备列表
     */
    List<ProjectDevice> getProjectDeviceList(ProjectDeviceExportReqVO exportReqVO);

}
