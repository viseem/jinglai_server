package cn.iocoder.yudao.module.jl.service.asset;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.asset.vo.*;
import cn.iocoder.yudao.module.jl.entity.asset.AssetDeviceLog;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 公司资产（设备）预约 Service 接口
 *
 */
public interface AssetDeviceLogService {

    /**
     * 创建公司资产（设备）预约
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAssetDeviceLog(@Valid AssetDeviceLogCreateReqVO createReqVO);

    /**
     * 更新公司资产（设备）预约
     *
     * @param updateReqVO 更新信息
     */
    void updateAssetDeviceLog(@Valid AssetDeviceLogUpdateReqVO updateReqVO);

    /**
     * 删除公司资产（设备）预约
     *
     * @param id 编号
     */
    void deleteAssetDeviceLog(Long id);

    /**
     * 获得公司资产（设备）预约
     *
     * @param id 编号
     * @return 公司资产（设备）预约
     */
    Optional<AssetDeviceLog> getAssetDeviceLog(Long id);

    /**
     * 获得公司资产（设备）预约列表
     *
     * @param ids 编号
     * @return 公司资产（设备）预约列表
     */
    List<AssetDeviceLog> getAssetDeviceLogList(Collection<Long> ids);

    /**
     * 获得公司资产（设备）预约分页
     *
     * @param pageReqVO 分页查询
     * @return 公司资产（设备）预约分页
     */
    PageResult<AssetDeviceLog> getAssetDeviceLogPage(AssetDeviceLogPageReqVO pageReqVO, AssetDeviceLogPageOrder orderV0);

    /**
     * 获得公司资产（设备）预约列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 公司资产（设备）预约列表
     */
    List<AssetDeviceLog> getAssetDeviceLogList(AssetDeviceLogExportReqVO exportReqVO);

}
