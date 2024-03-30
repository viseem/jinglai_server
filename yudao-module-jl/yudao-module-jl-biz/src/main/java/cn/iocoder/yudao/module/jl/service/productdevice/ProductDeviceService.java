package cn.iocoder.yudao.module.jl.service.productdevice;

import java.util.*;
import javax.validation.*;
import cn.iocoder.yudao.module.jl.controller.admin.productdevice.vo.*;
import cn.iocoder.yudao.module.jl.entity.productdevice.ProductDevice;
import cn.iocoder.yudao.framework.common.pojo.PageResult;

/**
 * 产品库设备 Service 接口
 *
 */
public interface ProductDeviceService {

    /**
     * 创建产品库设备
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createProductDevice(@Valid ProductDeviceCreateReqVO createReqVO);

    /**
     * 更新产品库设备
     *
     * @param updateReqVO 更新信息
     */
    void updateProductDevice(@Valid ProductDeviceUpdateReqVO updateReqVO);

    /**
     * 删除产品库设备
     *
     * @param id 编号
     */
    void deleteProductDevice(Long id);

    /**
     * 获得产品库设备
     *
     * @param id 编号
     * @return 产品库设备
     */
    Optional<ProductDevice> getProductDevice(Long id);

    /**
     * 获得产品库设备列表
     *
     * @param ids 编号
     * @return 产品库设备列表
     */
    List<ProductDevice> getProductDeviceList(Collection<Long> ids);

    /**
     * 获得产品库设备分页
     *
     * @param pageReqVO 分页查询
     * @return 产品库设备分页
     */
    PageResult<ProductDevice> getProductDevicePage(ProductDevicePageReqVO pageReqVO, ProductDevicePageOrder orderV0);

    /**
     * 获得产品库设备列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 产品库设备列表
     */
    List<ProductDevice> getProductDeviceList(ProductDeviceExportReqVO exportReqVO);

}
