package cn.iocoder.yudao.module.jl.controller.admin.project;

import cn.iocoder.yudao.module.system.controller.admin.user.vo.user.UserImportExcelVO;
import cn.iocoder.yudao.module.system.controller.admin.user.vo.user.UserImportRespVO;
import io.swagger.v3.oas.annotations.Parameters;
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
import cn.iocoder.yudao.module.jl.entity.project.Supplier;
import cn.iocoder.yudao.module.jl.mapper.project.SupplierMapper;
import cn.iocoder.yudao.module.jl.service.project.SupplierService;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "管理后台 - 项目采购单物流信息")
@RestController
@RequestMapping("/jl/supplier")
@Validated
public class SupplierController {

    @Resource
    private SupplierService supplierService;

    @Resource
    private SupplierMapper supplierMapper;

    @PostMapping("/create")
    @Operation(summary = "创建项目采购单物流信息")
    @PreAuthorize("@ss.hasPermission('jl:supplier:create')")
    public CommonResult<Long> createSupplier(@Valid @RequestBody SupplierCreateReqVO createReqVO) {
        return success(supplierService.createSupplier(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目采购单物流信息")
    @PreAuthorize("@ss.hasPermission('jl:supplier:update')")
    public CommonResult<Boolean> updateSupplier(@Valid @RequestBody SupplierUpdateReqVO updateReqVO) {
        supplierService.updateSupplier(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除项目采购单物流信息")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:supplier:delete')")
    public CommonResult<Boolean> deleteSupplier(@RequestParam("id") Long id) {
        supplierService.deleteSupplier(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得项目采购单物流信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:supplier:query')")
    public CommonResult<SupplierRespVO> getSupplier(@RequestParam("id") Long id) {
            Optional<Supplier> supplier = supplierService.getSupplier(id);
        return success(supplier.map(supplierMapper::toDto).orElseThrow(() -> exception(SUPPLIER_NOT_EXISTS)));
    }

    @GetMapping("/get-stats")
    @Operation(summary = "通过 ID 获得项目采购单物流信息")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:supplier:query')")
    public CommonResult<SupplierStatsRespVO> getSupplierStats(@RequestParam("id") Long id) {
        SupplierStatsRespVO supplierStats = supplierService.getSupplierStats(id);
        return success(supplierStats);
    }


    @GetMapping("/page")
    @Operation(summary = "(分页)获得项目采购单物流信息列表")
    @PreAuthorize("@ss.hasPermission('jl:supplier:query')")
    public CommonResult<PageResult<SupplierRespVO>> getSupplierPage(@Valid SupplierPageReqVO pageVO, @Valid SupplierPageOrder orderV0) {
        PageResult<Supplier> pageResult = supplierService.getSupplierPage(pageVO, orderV0);
        return success(supplierMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目采购单物流信息 Excel")
    @PreAuthorize("@ss.hasPermission('jl:supplier:export')")
    @OperateLog(type = EXPORT)
    public void exportSupplierExcel(@Valid SupplierExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<Supplier> list = supplierService.getSupplierList(exportReqVO);
        // 导出 Excel
        List<SupplierExcelVO> excelData = supplierMapper.toExcelList(list);
        ExcelUtils.write(response, "项目采购单物流信息.xls", "数据", SupplierExcelVO.class, excelData);
    }

    //excel导入
    @PostMapping("/import-excel")
    @Operation(summary = "导入用户")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    @PreAuthorize("@ss.hasPermission('system:user:import')")
    public CommonResult<SupplierImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
                                                      @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
        List<SupplierImportVO> list = ExcelUtils.read(file, SupplierImportVO.class);
        return success(supplierService.importList(list, updateSupport));
    }

}
