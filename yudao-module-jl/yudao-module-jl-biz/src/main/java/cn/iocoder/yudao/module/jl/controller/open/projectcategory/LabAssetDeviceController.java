package cn.iocoder.yudao.module.jl.controller.open.projectcategory;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.asset.vo.AssetDevicePageOrder;
import cn.iocoder.yudao.module.jl.controller.admin.asset.vo.AssetDevicePageReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.asset.vo.AssetDeviceRespVO;
import cn.iocoder.yudao.module.jl.entity.asset.AssetDevice;
import cn.iocoder.yudao.module.jl.entity.asset.AssetDeviceLog;
import cn.iocoder.yudao.module.jl.mapper.asset.AssetDeviceMapper;
import cn.iocoder.yudao.module.jl.repository.asset.AssetDeviceLogRepository;
import cn.iocoder.yudao.module.jl.service.asset.AssetDeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;

import java.time.LocalDateTime;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 公司资产（设备）")
@RestController
@RequestMapping("/lab/asset-device")
@Validated
public class LabAssetDeviceController {

    @Resource
    private AssetDeviceService assetDeviceService;

    @Resource
    private AssetDeviceMapper assetDeviceMapper;

    @Resource
    private AssetDeviceLogRepository assetDeviceLogRepository;

    @GetMapping("/page")
    @PermitAll
    @Operation(summary = "(分页)获得公司资产（设备）列表")
    public CommonResult<PageResult<AssetDeviceRespVO>> getAssetDevicePage(@Valid AssetDevicePageReqVO pageVO, @Valid AssetDevicePageOrder orderV0) {
        pageVO.setPageSize(100);
        PageResult<AssetDevice> pageResult = assetDeviceService.getAssetDevicePage(pageVO, orderV0);
        pageResult.getList().forEach(assetDevice -> {
            //获取当前时间 判断是否在使用中
            AssetDeviceLog assetDeviceLog = assetDeviceLogRepository.findByDeviceIdAndStartDateLessThanAndEndDateGreaterThan(assetDevice.getId(), LocalDateTime.now());
            assetDevice.setBusy(assetDeviceLog!=null);
        });
        return success(assetDeviceMapper.toPage(pageResult));
    }


}
