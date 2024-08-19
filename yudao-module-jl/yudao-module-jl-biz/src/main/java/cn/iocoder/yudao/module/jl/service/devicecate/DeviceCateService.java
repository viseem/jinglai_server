package cn.iocoder.yudao.module.jl.service.devicecate;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.devicecate.vo.*;
import cn.iocoder.yudao.module.jl.entity.devicecate.DeviceCate;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 设备分类 Service 接口
 *
 */
public interface DeviceCateService {

    /**
     * 创建设备分类
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createDeviceCate(@Valid DeviceCateCreateReqVO createReqVO);

    /**
     * 更新设备分类
     *
     * @param updateReqVO 更新信息
     */
    void updateDeviceCate(@Valid DeviceCateUpdateReqVO updateReqVO);

    /**
     * 删除设备分类
     *
     * @param id 编号
     */
    void deleteDeviceCate(Long id);

    /**
     * 获得设备分类
     *
     * @param id 编号
     * @return 设备分类
     */
    Optional<DeviceCate> getDeviceCate(Long id);

    /**
     * 获得设备分类列表
     *
     * @param ids 编号
     * @return 设备分类列表
     */
    List<DeviceCate> getDeviceCateList(Collection<Long> ids);

    /**
     * 获得设备分类分页
     *
     * @param pageReqVO 分页查询
     * @return 设备分类分页
     */
    PageResult<DeviceCate> getDeviceCatePage(DeviceCatePageReqVO pageReqVO, DeviceCatePageOrder orderV0);

    /**
     * 获得设备分类列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 设备分类列表
     */
    List<DeviceCate> getDeviceCateList(DeviceCateExportReqVO exportReqVO);

}
