package cn.iocoder.yudao.module.jl.controller.admin.picollaborationitem;

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

import cn.iocoder.yudao.module.jl.controller.admin.picollaborationitem.vo.*;
import cn.iocoder.yudao.module.jl.entity.picollaborationitem.PiCollaborationItem;
import cn.iocoder.yudao.module.jl.mapper.picollaborationitem.PiCollaborationItemMapper;
import cn.iocoder.yudao.module.jl.service.picollaborationitem.PiCollaborationItemService;

@Tag(name = "管理后台 - pi组协作明细")
@RestController
@RequestMapping("/jl/pi-collaboration-item")
@Validated
public class PiCollaborationItemController {

    @Resource
    private PiCollaborationItemService piCollaborationItemService;

    @Resource
    private PiCollaborationItemMapper piCollaborationItemMapper;

    @PostMapping("/create")
    @Operation(summary = "创建pi组协作明细")
    @PreAuthorize("@ss.hasPermission('jl:pi-collaboration-item:create')")
    public CommonResult<Long> createPiCollaborationItem(@Valid @RequestBody PiCollaborationItemCreateReqVO createReqVO) {
        return success(piCollaborationItemService.createPiCollaborationItem(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新pi组协作明细")
    @PreAuthorize("@ss.hasPermission('jl:pi-collaboration-item:update')")
    public CommonResult<Boolean> updatePiCollaborationItem(@Valid @RequestBody PiCollaborationItemUpdateReqVO updateReqVO) {
        piCollaborationItemService.updatePiCollaborationItem(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除pi组协作明细")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:pi-collaboration-item:delete')")
    public CommonResult<Boolean> deletePiCollaborationItem(@RequestParam("id") Long id) {
        piCollaborationItemService.deletePiCollaborationItem(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得pi组协作明细")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:pi-collaboration-item:query')")
    public CommonResult<PiCollaborationItemRespVO> getPiCollaborationItem(@RequestParam("id") Long id) {
            Optional<PiCollaborationItem> piCollaborationItem = piCollaborationItemService.getPiCollaborationItem(id);
        return success(piCollaborationItem.map(piCollaborationItemMapper::toDto).orElseThrow(() -> exception(PI_COLLABORATION_ITEM_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得pi组协作明细列表")
    @PreAuthorize("@ss.hasPermission('jl:pi-collaboration-item:query')")
    public CommonResult<PageResult<PiCollaborationItemRespVO>> getPiCollaborationItemPage(@Valid PiCollaborationItemPageReqVO pageVO, @Valid PiCollaborationItemPageOrder orderV0) {
        PageResult<PiCollaborationItem> pageResult = piCollaborationItemService.getPiCollaborationItemPage(pageVO, orderV0);
        return success(piCollaborationItemMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出pi组协作明细 Excel")
    @PreAuthorize("@ss.hasPermission('jl:pi-collaboration-item:export')")
    @OperateLog(type = EXPORT)
    public void exportPiCollaborationItemExcel(@Valid PiCollaborationItemExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<PiCollaborationItem> list = piCollaborationItemService.getPiCollaborationItemList(exportReqVO);
        // 导出 Excel
        List<PiCollaborationItemExcelVO> excelData = piCollaborationItemMapper.toExcelList(list);
        ExcelUtils.write(response, "pi组协作明细.xls", "数据", PiCollaborationItemExcelVO.class, excelData);
    }

}
