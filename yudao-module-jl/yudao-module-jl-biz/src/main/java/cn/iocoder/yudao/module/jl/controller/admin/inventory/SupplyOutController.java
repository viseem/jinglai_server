package cn.iocoder.yudao.module.jl.controller.admin.inventory;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

import javax.validation.constraints.*;
import javax.validation.*;
import javax.servlet.http.*;
import java.util.*;
import java.io.IOException;

import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.*;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;

import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.*;

import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.SupplyOut;
import cn.iocoder.yudao.module.jl.mapper.inventory.SupplyOutMapper;
import cn.iocoder.yudao.module.jl.service.inventory.SupplyOutService;

@Tag(name = "管理后台 - 出库申请")
@RestController
@RequestMapping("/jl/supply-out")
@Validated
public class SupplyOutController {

    @Resource
    private SupplyOutService supplyOutService;

    @Resource
    private SupplyOutMapper supplyOutMapper;


    @PostMapping("/save")
    @Operation(summary = "创建出库申请")
    @PreAuthorize("@ss.hasPermission('jl:supply-out:create')")
    public CommonResult<Long> saveSupplyOut(@Valid @RequestBody SupplyOutSaveReqVO saveReqVO) {
        return success(supplyOutService.saveSupplyOut(saveReqVO));
    }

    @PostMapping("/create")
    @Operation(summary = "创建出库申请")
    @PreAuthorize("@ss.hasPermission('jl:supply-out:create')")
    public CommonResult<Long> createSupplyOut(@Valid @RequestBody SupplyOutCreateReqVO createReqVO) {
        return success(supplyOutService.createSupplyOut(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新出库申请")
    @PreAuthorize("@ss.hasPermission('jl:supply-out:update')")
    public CommonResult<Boolean> updateSupplyOut(@Valid @RequestBody SupplyOutUpdateReqVO updateReqVO) {
        supplyOutService.updateSupplyOut(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除出库申请")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:supply-out:delete')")
    public CommonResult<Boolean> deleteSupplyOut(@RequestParam("id") Long id) {
        supplyOutService.deleteSupplyOut(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得出库申请")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:supply-out:query')")
    public CommonResult<SupplyOutRespVO> getSupplyOut(@RequestParam("id") Long id) {
            Optional<SupplyOut> supplyOut = supplyOutService.getSupplyOut(id);
        return success(supplyOut.map(supplyOutMapper::toDto).orElseThrow(() -> exception(SUPPLY_OUT_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得出库申请列表")
    @PreAuthorize("@ss.hasPermission('jl:supply-out:query')")
    public CommonResult<PageResult<SupplyOutRespVO>> getSupplyOutPage(@Valid SupplyOutPageReqVO pageVO, @Valid SupplyOutPageOrder orderV0) {
        PageResult<SupplyOut> pageResult = supplyOutService.getSupplyOutPage(pageVO, orderV0);
        return success(supplyOutMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出出库申请 Excel")
    @PreAuthorize("@ss.hasPermission('jl:supply-out:export')")
    @OperateLog(type = EXPORT)
    public void exportSupplyOutExcel(@Valid SupplyOutExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<SupplyOut> list = supplyOutService.getSupplyOutList(exportReqVO);
        // 导出 Excel
        List<SupplyOutExcelVO> excelData = supplyOutMapper.toExcelList(list);
        ExcelUtils.write(response, "出库申请.xls", "数据", SupplyOutExcelVO.class, excelData);
    }

}
