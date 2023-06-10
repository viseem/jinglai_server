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
import cn.iocoder.yudao.module.jl.entity.project.SupplySendInItem;
import cn.iocoder.yudao.module.jl.mapper.project.SupplySendInItemMapper;
import cn.iocoder.yudao.module.jl.service.project.SupplySendInItemService;

@Tag(name = "管理后台 - 物资寄来单申请明细")
@RestController
@RequestMapping("/jl/supply-send-in-item")
@Validated
public class SupplySendInItemController {

    @Resource
    private SupplySendInItemService supplySendInItemService;

    @Resource
    private SupplySendInItemMapper supplySendInItemMapper;

    @PostMapping("/create")
    @Operation(summary = "创建物资寄来单申请明细")
    @PreAuthorize("@ss.hasPermission('jl:supply-send-in-item:create')")
    public CommonResult<Long> createSupplySendInItem(@Valid @RequestBody SupplySendInItemCreateReqVO createReqVO) {
        return success(supplySendInItemService.createSupplySendInItem(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新物资寄来单申请明细")
    @PreAuthorize("@ss.hasPermission('jl:supply-send-in-item:update')")
    public CommonResult<Boolean> updateSupplySendInItem(@Valid @RequestBody SupplySendInItemUpdateReqVO updateReqVO) {
        supplySendInItemService.updateSupplySendInItem(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除物资寄来单申请明细")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:supply-send-in-item:delete')")
    public CommonResult<Boolean> deleteSupplySendInItem(@RequestParam("id") Long id) {
        supplySendInItemService.deleteSupplySendInItem(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得物资寄来单申请明细")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:supply-send-in-item:query')")
    public CommonResult<SupplySendInItemRespVO> getSupplySendInItem(@RequestParam("id") Long id) {
            Optional<SupplySendInItem> supplySendInItem = supplySendInItemService.getSupplySendInItem(id);
        return success(supplySendInItem.map(supplySendInItemMapper::toDto).orElseThrow(() -> exception(SUPPLY_SEND_IN_ITEM_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得物资寄来单申请明细列表")
    @PreAuthorize("@ss.hasPermission('jl:supply-send-in-item:query')")
    public CommonResult<PageResult<SupplySendInItemRespVO>> getSupplySendInItemPage(@Valid SupplySendInItemPageReqVO pageVO, @Valid SupplySendInItemPageOrder orderV0) {
        PageResult<SupplySendInItem> pageResult = supplySendInItemService.getSupplySendInItemPage(pageVO, orderV0);
        return success(supplySendInItemMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出物资寄来单申请明细 Excel")
    @PreAuthorize("@ss.hasPermission('jl:supply-send-in-item:export')")
    @OperateLog(type = EXPORT)
    public void exportSupplySendInItemExcel(@Valid SupplySendInItemExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<SupplySendInItem> list = supplySendInItemService.getSupplySendInItemList(exportReqVO);
        // 导出 Excel
        List<SupplySendInItemExcelVO> excelData = supplySendInItemMapper.toExcelList(list);
        ExcelUtils.write(response, "物资寄来单申请明细.xls", "数据", SupplySendInItemExcelVO.class, excelData);
    }

}
