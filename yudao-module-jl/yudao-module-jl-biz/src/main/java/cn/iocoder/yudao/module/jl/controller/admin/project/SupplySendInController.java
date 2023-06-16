package cn.iocoder.yudao.module.jl.controller.admin.project;

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

import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.entity.project.SupplySendIn;
import cn.iocoder.yudao.module.jl.mapper.project.SupplySendInMapper;
import cn.iocoder.yudao.module.jl.service.project.SupplySendInService;

@Tag(name = "管理后台 - 物资寄来单申请")
@RestController
@RequestMapping("/jl/supply-send-in")
@Validated
public class SupplySendInController {

    @Resource
    private SupplySendInService supplySendInService;

    @Resource
    private SupplySendInMapper supplySendInMapper;

//    @PostMapping("/create")
//    @Operation(summary = "创建物资寄来单申请")
//    @PreAuthorize("@ss.hasPermission('jl:supply-send-in:create')")
//    public CommonResult<Long> createSupplySendIn(@Valid @RequestBody SupplySendInCreateReqVO createReqVO) {
//        return success(supplySendInService.createSupplySendIn(createReqVO));
//    }
//
//    @PutMapping("/update")
//    @Operation(summary = "更新物资寄来单申请")
//    @PreAuthorize("@ss.hasPermission('jl:supply-send-in:update')")
//    public CommonResult<Boolean> updateSupplySendIn(@Valid @RequestBody SupplySendInUpdateReqVO updateReqVO) {
//        supplySendInService.updateSupplySendIn(updateReqVO);
//        return success(true);
//    }

    @PutMapping("/save")
    @Operation(summary = "全量更新物资寄来单申请")
    @PreAuthorize("@ss.hasPermission('jl:supply-send-in:update')")
    public CommonResult<Boolean> updateSupplySendIn(@Valid @RequestBody SupplySendInSaveReqVO saveReqVO) {
        supplySendInService.saveSupplySendIn(saveReqVO);
        return success(true);
    }

//    @PutMapping("/check-in")
//    @Operation(summary = "签收物品")
//    @PreAuthorize("@ss.hasPermission('jl:procurement:update')")
//    public CommonResult<Boolean> checkInShipmentProcurement(@Valid @RequestBody SendInCheckInReqVO saveReqVO) {
//        supplySendInService.checkIn(saveReqVO);
//        return success(true);
//    }
//
//    @PutMapping("/store-in")
//    @Operation(summary = "签收物品")
//    @PreAuthorize("@ss.hasPermission('jl:procurement:update')")
//    public CommonResult<Boolean> storeProcurementItem(@Valid @RequestBody StoreInProcurementItemReqVO saveReqVO) {
//        procurementService.storeIn(saveReqVO);
//        return success(true);
//    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除物资寄来单申请")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:supply-send-in:delete')")
    public CommonResult<Boolean> deleteSupplySendIn(@RequestParam("id") Long id) {
        supplySendInService.deleteSupplySendIn(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得物资寄来单申请")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:supply-send-in:query')")
    public CommonResult<SupplySendInRespVO> getSupplySendIn(@RequestParam("id") Long id) {
        Optional<SupplySendIn> supplySendIn = supplySendInService.getSupplySendIn(id);
        return success(supplySendIn.map(supplySendInMapper::toDto).orElseThrow(() -> exception(SUPPLY_SEND_IN_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得物资寄来单申请列表")
    @PreAuthorize("@ss.hasPermission('jl:supply-send-in:query')")
    public CommonResult<PageResult<SupplySendInRespVO>> getSupplySendInPage(@Valid SupplySendInPageReqVO pageVO, @Valid SupplySendInPageOrder orderV0) {
        PageResult<SupplySendIn> pageResult = supplySendInService.getSupplySendInPage(pageVO, orderV0);
        return success(supplySendInMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出物资寄来单申请 Excel")
    @PreAuthorize("@ss.hasPermission('jl:supply-send-in:export')")
    @OperateLog(type = EXPORT)
    public void exportSupplySendInExcel(@Valid SupplySendInExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<SupplySendIn> list = supplySendInService.getSupplySendInList(exportReqVO);
        // 导出 Excel
        List<SupplySendInExcelVO> excelData = supplySendInMapper.toExcelList(list);
        ExcelUtils.write(response, "物资寄来单申请.xls", "数据", SupplySendInExcelVO.class, excelData);
    }

}
