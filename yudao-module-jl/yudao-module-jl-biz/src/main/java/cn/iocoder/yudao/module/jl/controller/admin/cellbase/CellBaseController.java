package cn.iocoder.yudao.module.jl.controller.admin.cellbase;

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

import cn.iocoder.yudao.module.jl.controller.admin.cellbase.vo.*;
import cn.iocoder.yudao.module.jl.entity.cellbase.CellBase;
import cn.iocoder.yudao.module.jl.mapper.cellbase.CellBaseMapper;
import cn.iocoder.yudao.module.jl.service.cellbase.CellBaseService;

@Tag(name = "管理后台 - 细胞数据")
@RestController
@RequestMapping("/jl/cell-base")
@Validated
public class CellBaseController {

    @Resource
    private CellBaseService cellBaseService;

    @Resource
    private CellBaseMapper cellBaseMapper;

    @PostMapping("/create")
    @Operation(summary = "创建细胞数据")
    @PreAuthorize("@ss.hasPermission('jl:cell-base:create')")
    public CommonResult<Long> createCellBase(@Valid @RequestBody CellBaseCreateReqVO createReqVO) {
        return success(cellBaseService.createCellBase(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新细胞数据")
    @PreAuthorize("@ss.hasPermission('jl:cell-base:update')")
    public CommonResult<Boolean> updateCellBase(@Valid @RequestBody CellBaseUpdateReqVO updateReqVO) {
        cellBaseService.updateCellBase(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除细胞数据")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:cell-base:delete')")
    public CommonResult<Boolean> deleteCellBase(@RequestParam("id") Long id) {
        cellBaseService.deleteCellBase(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得细胞数据")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:cell-base:query')")
    public CommonResult<CellBaseRespVO> getCellBase(@RequestParam("id") Long id) {
            Optional<CellBase> cellBase = cellBaseService.getCellBase(id);
        return success(cellBase.map(cellBaseMapper::toDto).orElseThrow(() -> exception(CELL_BASE_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得细胞数据列表")
    @PreAuthorize("@ss.hasPermission('jl:cell-base:query')")
    public CommonResult<PageResult<CellBaseRespVO>> getCellBasePage(@Valid CellBasePageReqVO pageVO, @Valid CellBasePageOrder orderV0) {
        PageResult<CellBase> pageResult = cellBaseService.getCellBasePage(pageVO, orderV0);
        return success(cellBaseMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出细胞数据 Excel")
    @PreAuthorize("@ss.hasPermission('jl:cell-base:export')")
    @OperateLog(type = EXPORT)
    public void exportCellBaseExcel(@Valid CellBaseExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<CellBase> list = cellBaseService.getCellBaseList(exportReqVO);
        // 导出 Excel
        List<CellBaseExcelVO> excelData = cellBaseMapper.toExcelList(list);
        ExcelUtils.write(response, "细胞数据.xls", "数据", CellBaseExcelVO.class, excelData);
    }

}
