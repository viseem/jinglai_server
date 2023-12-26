package cn.iocoder.yudao.module.jl.controller.open.projectcategory;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.jl.controller.admin.asset.vo.*;
import cn.iocoder.yudao.module.jl.entity.asset.AssetDevice;
import cn.iocoder.yudao.module.jl.mapper.asset.AssetDeviceMapper;
import cn.iocoder.yudao.module.jl.service.asset.AssetDeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.ASSET_DEVICE_NOT_EXISTS;

@Tag(name = "管理后台 - 公司资产（设备）")
@RestController
@RequestMapping("/lab/asset-device")
@Validated
public class LabAssetDeviceController {

    @Resource
    private AssetDeviceService assetDeviceService;

    @Resource
    private AssetDeviceMapper assetDeviceMapper;

    @GetMapping("/page")
    @PermitAll
    @Operation(summary = "(分页)获得公司资产（设备）列表")
    public CommonResult<PageResult<AssetDeviceRespVO>> getAssetDevicePage(@Valid AssetDevicePageReqVO pageVO, @Valid AssetDevicePageOrder orderV0) {
        pageVO.setPageSize(100);
        PageResult<AssetDevice> pageResult = assetDeviceService.getAssetDevicePage(pageVO, orderV0);
        return success(assetDeviceMapper.toPage(pageResult));
    }


}
