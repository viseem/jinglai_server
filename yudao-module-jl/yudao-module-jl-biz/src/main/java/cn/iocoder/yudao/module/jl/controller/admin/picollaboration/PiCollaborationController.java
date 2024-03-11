package cn.iocoder.yudao.module.jl.controller.admin.picollaboration;

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

import cn.iocoder.yudao.module.jl.controller.admin.picollaboration.vo.*;
import cn.iocoder.yudao.module.jl.entity.picollaboration.PiCollaboration;
import cn.iocoder.yudao.module.jl.mapper.picollaboration.PiCollaborationMapper;
import cn.iocoder.yudao.module.jl.service.picollaboration.PiCollaborationService;

@Tag(name = "管理后台 - PI组协作")
@RestController
@RequestMapping("/jl/pi-collaboration")
@Validated
public class PiCollaborationController {

    @Resource
    private PiCollaborationService piCollaborationService;

    @Resource
    private PiCollaborationMapper piCollaborationMapper;

    @PostMapping("/create")
    @Operation(summary = "创建PI组协作")
    @PreAuthorize("@ss.hasPermission('jl:pi-collaboration:create')")
    public CommonResult<Long> createPiCollaboration(@Valid @RequestBody PiCollaborationCreateReqVO createReqVO) {
        return success(piCollaborationService.createPiCollaboration(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新PI组协作")
    @PreAuthorize("@ss.hasPermission('jl:pi-collaboration:update')")
    public CommonResult<Boolean> updatePiCollaboration(@Valid @RequestBody PiCollaborationUpdateReqVO updateReqVO) {
        piCollaborationService.updatePiCollaboration(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除PI组协作")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:pi-collaboration:delete')")
    public CommonResult<Boolean> deletePiCollaboration(@RequestParam("id") Long id) {
        piCollaborationService.deletePiCollaboration(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得PI组协作")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:pi-collaboration:query')")
    public CommonResult<PiCollaborationRespVO> getPiCollaboration(@RequestParam("id") Long id) {
            Optional<PiCollaboration> piCollaboration = piCollaborationService.getPiCollaboration(id);
        return success(piCollaboration.map(piCollaborationMapper::toDto).orElseThrow(() -> exception(PI_COLLABORATION_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得PI组协作列表")
    @PreAuthorize("@ss.hasPermission('jl:pi-collaboration:query')")
    public CommonResult<PageResult<PiCollaborationRespVO>> getPiCollaborationPage(@Valid PiCollaborationPageReqVO pageVO, @Valid PiCollaborationPageOrder orderV0) {
        PageResult<PiCollaboration> pageResult = piCollaborationService.getPiCollaborationPage(pageVO, orderV0);
        return success(piCollaborationMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出PI组协作 Excel")
    @PreAuthorize("@ss.hasPermission('jl:pi-collaboration:export')")
    @OperateLog(type = EXPORT)
    public void exportPiCollaborationExcel(@Valid PiCollaborationExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<PiCollaboration> list = piCollaborationService.getPiCollaborationList(exportReqVO);
        // 导出 Excel
        List<PiCollaborationExcelVO> excelData = piCollaborationMapper.toExcelList(list);
        ExcelUtils.write(response, "PI组协作.xls", "数据", PiCollaborationExcelVO.class, excelData);
    }

}
