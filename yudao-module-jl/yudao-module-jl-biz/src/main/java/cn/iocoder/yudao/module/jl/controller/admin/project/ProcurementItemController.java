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
import cn.iocoder.yudao.module.jl.entity.project.ProcurementItem;
import cn.iocoder.yudao.module.jl.mapper.project.ProcurementItemMapper;
import cn.iocoder.yudao.module.jl.service.project.ProcurementItemService;

@Tag(name = "管理后台 - 项目采购单申请明细")
@RestController
@RequestMapping("/jl/procurement-item")
@Validated
public class ProcurementItemController {

    @Resource
    private ProcurementItemService procurementItemService;

    @Resource
    private ProcurementItemMapper procurementItemMapper;

    @PostMapping("/create")
    @Operation(summary = "创建项目采购单申请明细")
    @PreAuthorize("@ss.hasPermission('jl:procurement-item:create')")
    public CommonResult<Long> createProcurementItem(@Valid @RequestBody ProcurementItemCreateReqVO createReqVO) {
        return success(procurementItemService.createProcurementItem(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目采购单申请明细")
    @PreAuthorize("@ss.hasPermission('jl:procurement-item:update')")
    public CommonResult<Boolean> updateProcurementItem(@Valid @RequestBody ProcurementItemUpdateReqVO updateReqVO) {
        procurementItemService.updateProcurementItem(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除项目采购单申请明细")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:procurement-item:delete')")
    public CommonResult<Boolean> deleteProcurementItem(@RequestParam("id") Long id) {
        procurementItemService.deleteProcurementItem(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得项目采购单申请明细")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:procurement-item:query')")
    public CommonResult<ProcurementItemRespVO> getProcurementItem(@RequestParam("id") Long id) {
            Optional<ProcurementItem> procurementItem = procurementItemService.getProcurementItem(id);
        return success(procurementItem.map(procurementItemMapper::toDto).orElseThrow(() -> exception(PROCUREMENT_ITEM_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得项目采购单申请明细列表")
    @PreAuthorize("@ss.hasPermission('jl:procurement-item:query')")
    public CommonResult<PageResult<ProcurementItemRespVO>> getProcurementItemPage(@Valid ProcurementItemPageReqVO pageVO, @Valid ProcurementItemPageOrder orderV0) {
        PageResult<ProcurementItem> pageResult = procurementItemService.getProcurementItemPage(pageVO, orderV0);
        return success(procurementItemMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目采购单申请明细 Excel")
    @PreAuthorize("@ss.hasPermission('jl:procurement-item:export')")
    @OperateLog(type = EXPORT)
    public void exportProcurementItemExcel(@Valid ProcurementItemExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProcurementItem> list = procurementItemService.getProcurementItemList(exportReqVO);
        // 导出 Excel
        List<ProcurementItemExcelVO> excelData = procurementItemMapper.toExcelList(list);
        ExcelUtils.write(response, "项目采购单申请明细.xls", "数据", ProcurementItemExcelVO.class, excelData);
    }

}
