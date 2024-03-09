package cn.iocoder.yudao.module.jl.controller.admin.salesgroup;

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

import cn.iocoder.yudao.module.jl.controller.admin.salesgroup.vo.*;
import cn.iocoder.yudao.module.jl.entity.salesgroup.SalesGroup;
import cn.iocoder.yudao.module.jl.mapper.salesgroup.SalesGroupMapper;
import cn.iocoder.yudao.module.jl.service.salesgroup.SalesGroupService;

@Tag(name = "管理后台 - 销售分组")
@RestController
@RequestMapping("/jl/sales-group")
@Validated
public class SalesGroupController {

    @Resource
    private SalesGroupService salesGroupService;

    @Resource
    private SalesGroupMapper salesGroupMapper;

    @PostMapping("/create")
    @Operation(summary = "创建销售分组")
    @PreAuthorize("@ss.hasPermission('jl:sales-group:create')")
    public CommonResult<Long> createSalesGroup(@Valid @RequestBody SalesGroupCreateReqVO createReqVO) {
        return success(salesGroupService.createSalesGroup(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新销售分组")
    @PreAuthorize("@ss.hasPermission('jl:sales-group:update')")
    public CommonResult<Boolean> updateSalesGroup(@Valid @RequestBody SalesGroupUpdateReqVO updateReqVO) {
        salesGroupService.updateSalesGroup(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除销售分组")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:sales-group:delete')")
    public CommonResult<Boolean> deleteSalesGroup(@RequestParam("id") Long id) {
        salesGroupService.deleteSalesGroup(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得销售分组")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:sales-group:query')")
    public CommonResult<SalesGroupRespVO> getSalesGroup(@RequestParam("id") Long id) {
            Optional<SalesGroup> salesGroup = salesGroupService.getSalesGroup(id);
        return success(salesGroup.map(salesGroupMapper::toDto).orElseThrow(() -> exception(SALES_GROUP_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得销售分组列表")
    @PreAuthorize("@ss.hasPermission('jl:sales-group:query')")
    public CommonResult<PageResult<SalesGroupRespVO>> getSalesGroupPage(@Valid SalesGroupPageReqVO pageVO, @Valid SalesGroupPageOrder orderV0) {
        PageResult<SalesGroup> pageResult = salesGroupService.getSalesGroupPage(pageVO, orderV0);
        return success(salesGroupMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出销售分组 Excel")
    @PreAuthorize("@ss.hasPermission('jl:sales-group:export')")
    @OperateLog(type = EXPORT)
    public void exportSalesGroupExcel(@Valid SalesGroupExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<SalesGroup> list = salesGroupService.getSalesGroupList(exportReqVO);
        // 导出 Excel
        List<SalesGroupExcelVO> excelData = salesGroupMapper.toExcelList(list);
        ExcelUtils.write(response, "销售分组.xls", "数据", SalesGroupExcelVO.class, excelData);
    }

}
