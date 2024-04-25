package cn.iocoder.yudao.module.jl.controller.admin.suppliertag;

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

import cn.iocoder.yudao.module.jl.controller.admin.suppliertag.vo.*;
import cn.iocoder.yudao.module.jl.entity.suppliertag.SupplierTag;
import cn.iocoder.yudao.module.jl.mapper.suppliertag.SupplierTagMapper;
import cn.iocoder.yudao.module.jl.service.suppliertag.SupplierTagService;

@Tag(name = "管理后台 - 供应商标签")
@RestController
@RequestMapping("/jl/supplier-tag")
@Validated
public class SupplierTagController {

    @Resource
    private SupplierTagService supplierTagService;

    @Resource
    private SupplierTagMapper supplierTagMapper;

    @PostMapping("/create")
    @Operation(summary = "创建供应商标签")
    @PreAuthorize("@ss.hasPermission('jl:supplier-tag:create')")
    public CommonResult<Long> createSupplierTag(@Valid @RequestBody SupplierTagCreateReqVO createReqVO) {
        return success(supplierTagService.createSupplierTag(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新供应商标签")
    @PreAuthorize("@ss.hasPermission('jl:supplier-tag:update')")
    public CommonResult<Boolean> updateSupplierTag(@Valid @RequestBody SupplierTagUpdateReqVO updateReqVO) {
        supplierTagService.updateSupplierTag(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除供应商标签")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:supplier-tag:delete')")
    public CommonResult<Boolean> deleteSupplierTag(@RequestParam("id") Long id) {
        supplierTagService.deleteSupplierTag(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得供应商标签")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:supplier-tag:query')")
    public CommonResult<SupplierTagRespVO> getSupplierTag(@RequestParam("id") Long id) {
            Optional<SupplierTag> supplierTag = supplierTagService.getSupplierTag(id);
        return success(supplierTag.map(supplierTagMapper::toDto).orElseThrow(() -> exception(SUPPLIER_TAG_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得供应商标签列表")
    @PreAuthorize("@ss.hasPermission('jl:supplier-tag:query')")
    public CommonResult<PageResult<SupplierTagRespVO>> getSupplierTagPage(@Valid SupplierTagPageReqVO pageVO, @Valid SupplierTagPageOrder orderV0) {
        PageResult<SupplierTag> pageResult = supplierTagService.getSupplierTagPage(pageVO, orderV0);
        return success(supplierTagMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出供应商标签 Excel")
    @PreAuthorize("@ss.hasPermission('jl:supplier-tag:export')")
    @OperateLog(type = EXPORT)
    public void exportSupplierTagExcel(@Valid SupplierTagExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<SupplierTag> list = supplierTagService.getSupplierTagList(exportReqVO);
        // 导出 Excel
        List<SupplierTagExcelVO> excelData = supplierTagMapper.toExcelList(list);
        ExcelUtils.write(response, "供应商标签.xls", "数据", SupplierTagExcelVO.class, excelData);
    }

}
