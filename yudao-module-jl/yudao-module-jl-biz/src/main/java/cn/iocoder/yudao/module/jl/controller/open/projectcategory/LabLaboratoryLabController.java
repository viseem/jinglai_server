package cn.iocoder.yudao.module.jl.controller.open.projectcategory;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.jl.controller.admin.laboratory.vo.*;
import cn.iocoder.yudao.module.jl.entity.laboratory.LaboratoryLab;
import cn.iocoder.yudao.module.jl.mapper.laboratory.LaboratoryLabMapper;
import cn.iocoder.yudao.module.jl.service.laboratory.LaboratoryLabService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.LABORATORY_LAB_NOT_EXISTS;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 实验室")
@RestController
@RequestMapping("/lab/laboratory-lab")
@Validated
public class LabLaboratoryLabController {

    @Resource
    private LaboratoryLabService laboratoryLabService;

    @Resource
    private LaboratoryLabMapper laboratoryLabMapper;

    @PostMapping("/create")
    @Operation(summary = "创建实验室")
    @PreAuthorize("@ss.hasPermission('jl:laboratory-lab:create')")
    public CommonResult<Long> createLaboratoryLab(@Valid @RequestBody LaboratoryLabCreateReqVO createReqVO) {
        return success(laboratoryLabService.createLaboratoryLab(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新实验室")
    @PreAuthorize("@ss.hasPermission('jl:laboratory-lab:update')")
    public CommonResult<Boolean> updateLaboratoryLab(@Valid @RequestBody LaboratoryLabUpdateReqVO updateReqVO) {
        laboratoryLabService.updateLaboratoryLab(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除实验室")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:laboratory-lab:delete')")
    public CommonResult<Boolean> deleteLaboratoryLab(@RequestParam("id") Long id) {
        laboratoryLabService.deleteLaboratoryLab(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得实验室")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:laboratory-lab:query')")
    public CommonResult<LaboratoryLabRespVO> getLaboratoryLab(@RequestParam("id") Long id) {
            Optional<LaboratoryLab> laboratoryLab = laboratoryLabService.getLaboratoryLab(id);
        return success(laboratoryLab.map(laboratoryLabMapper::toDto).orElseThrow(() -> exception(LABORATORY_LAB_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得实验室列表")
    public CommonResult<PageResult<LaboratoryLabRespVO>> getLaboratoryLabPage(@Valid LaboratoryLabPageReqVO pageVO, @Valid LaboratoryLabPageOrder orderV0) {
        System.out.println("------"+getLoginUserId());
        PageResult<LaboratoryLab> pageResult = laboratoryLabService.getLaboratoryLabPage(pageVO, orderV0);
        return success(laboratoryLabMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出实验室 Excel")
    @PreAuthorize("@ss.hasPermission('jl:laboratory-lab:export')")
    @OperateLog(type = EXPORT)
    public void exportLaboratoryLabExcel(@Valid LaboratoryLabExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<LaboratoryLab> list = laboratoryLabService.getLaboratoryLabList(exportReqVO);
        // 导出 Excel
        List<LaboratoryLabExcelVO> excelData = laboratoryLabMapper.toExcelList(list);
        ExcelUtils.write(response, "实验室.xls", "数据", LaboratoryLabExcelVO.class, excelData);
    }

}
