package cn.iocoder.yudao.module.jl.controller.admin.taskproduct;

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

import cn.iocoder.yudao.module.jl.controller.admin.taskproduct.vo.*;
import cn.iocoder.yudao.module.jl.entity.taskproduct.TaskProduct;
import cn.iocoder.yudao.module.jl.mapper.taskproduct.TaskProductMapper;
import cn.iocoder.yudao.module.jl.service.taskproduct.TaskProductService;

@Tag(name = "管理后台 - 任务产品中间")
@RestController
@RequestMapping("/jl/task-product")
@Validated
public class TaskProductController {

    @Resource
    private TaskProductService taskProductService;

    @Resource
    private TaskProductMapper taskProductMapper;

    @PostMapping("/create")
    @Operation(summary = "创建任务产品中间")
    @PreAuthorize("@ss.hasPermission('jl:task-product:create')")
    public CommonResult<Long> createTaskProduct(@Valid @RequestBody TaskProductCreateReqVO createReqVO) {
        return success(taskProductService.createTaskProduct(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新任务产品中间")
    @PreAuthorize("@ss.hasPermission('jl:task-product:update')")
    public CommonResult<Boolean> updateTaskProduct(@Valid @RequestBody TaskProductUpdateReqVO updateReqVO) {
        taskProductService.updateTaskProduct(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除任务产品中间")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:task-product:delete')")
    public CommonResult<Boolean> deleteTaskProduct(@RequestParam("id") Long id) {
        taskProductService.deleteTaskProduct(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得任务产品中间")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:task-product:query')")
    public CommonResult<TaskProductRespVO> getTaskProduct(@RequestParam("id") Long id) {
            Optional<TaskProduct> taskProduct = taskProductService.getTaskProduct(id);
        return success(taskProduct.map(taskProductMapper::toDto).orElseThrow(() -> exception(TASK_PRODUCT_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得任务产品中间列表")
    @PreAuthorize("@ss.hasPermission('jl:task-product:query')")
    public CommonResult<PageResult<TaskProductRespVO>> getTaskProductPage(@Valid TaskProductPageReqVO pageVO, @Valid TaskProductPageOrder orderV0) {
        PageResult<TaskProduct> pageResult = taskProductService.getTaskProductPage(pageVO, orderV0);
        return success(taskProductMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出任务产品中间 Excel")
    @PreAuthorize("@ss.hasPermission('jl:task-product:export')")
    @OperateLog(type = EXPORT)
    public void exportTaskProductExcel(@Valid TaskProductExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<TaskProduct> list = taskProductService.getTaskProductList(exportReqVO);
        // 导出 Excel
        List<TaskProductExcelVO> excelData = taskProductMapper.toExcelList(list);
        ExcelUtils.write(response, "任务产品中间.xls", "数据", TaskProductExcelVO.class, excelData);
    }

}
