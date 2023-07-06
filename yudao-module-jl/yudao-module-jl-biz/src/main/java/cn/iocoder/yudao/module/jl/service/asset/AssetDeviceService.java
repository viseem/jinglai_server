package cn.iocoder.yudao.module.jl.service.asset;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.asset.vo.*;
import cn.iocoder.yudao.module.jl.entity.asset.AssetDevice;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 公司资产（设备） Service 接口
 *
 */
public interface AssetDeviceService {

    /**
     * 创建公司资产（设备）
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAssetDevice(@Valid AssetDeviceCreateReqVO createReqVO);

    /**
     * 更新公司资产（设备）
     *
     * @param updateReqVO 更新信息
     */
    void updateAssetDevice(@Valid AssetDeviceUpdateReqVO updateReqVO);

    /**
     * 删除公司资产（设备）
     *
     * @param id 编号
     */
    void deleteAssetDevice(Long id);

    /**
     * 获得公司资产（设备）
     *
     * @param id 编号
     * @return 公司资产（设备）
     */
    Optional<AssetDevice> getAssetDevice(Long id);

    /**
     * 获得公司资产（设备）列表
     *
     * @param ids 编号
     * @return 公司资产（设备）列表
     */
    List<AssetDevice> getAssetDeviceList(Collection<Long> ids);

    /**
     * 获得公司资产（设备）分页
     *
     * @param pageReqVO 分页查询
     * @return 公司资产（设备）分页
     */
    PageResult<AssetDevice> getAssetDevicePage(AssetDevicePageReqVO pageReqVO, AssetDevicePageOrder orderV0);

    /**
     * 获得公司资产（设备）列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 公司资产（设备）列表
     */
    List<AssetDevice> getAssetDeviceList(AssetDeviceExportReqVO exportReqVO);

}
