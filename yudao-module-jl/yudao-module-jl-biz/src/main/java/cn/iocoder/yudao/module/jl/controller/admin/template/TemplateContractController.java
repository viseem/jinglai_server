package cn.iocoder.yudao.module.jl.controller.admin.template;

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

import cn.iocoder.yudao.module.jl.controller.admin.template.vo.*;
import cn.iocoder.yudao.module.jl.entity.template.TemplateContract;
import cn.iocoder.yudao.module.jl.mapper.template.TemplateContractMapper;
import cn.iocoder.yudao.module.jl.service.template.TemplateContractService;

@Tag(name = "管理后台 - 合同模板")
@RestController
@RequestMapping("/jl/template-contract")
@Validated
public class TemplateContractController {

    @Resource
    private TemplateContractService templateContractService;

    @Resource
    private TemplateContractMapper templateContractMapper;

    @PostMapping("/create")
    @Operation(summary = "创建合同模板")
    @PreAuthorize("@ss.hasPermission('jl:template-contract:create')")
    public CommonResult<Long> createTemplateContract(@Valid @RequestBody TemplateContractCreateReqVO createReqVO) {
        return success(templateContractService.createTemplateContract(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新合同模板")
    @PreAuthorize("@ss.hasPermission('jl:template-contract:update')")
    public CommonResult<Boolean> updateTemplateContract(@Valid @RequestBody TemplateContractUpdateReqVO updateReqVO) {
        templateContractService.updateTemplateContract(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除合同模板")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:template-contract:delete')")
    public CommonResult<Boolean> deleteTemplateContract(@RequestParam("id") Long id) {
        templateContractService.deleteTemplateContract(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得合同模板")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:template-contract:query')")
    public CommonResult<TemplateContractRespVO> getTemplateContract(@RequestParam("id") Long id) {
            Optional<TemplateContract> templateContract = templateContractService.getTemplateContract(id);
        return success(templateContract.map(templateContractMapper::toDto).orElseThrow(() -> exception(TEMPLATE_CONTRACT_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得合同模板列表")
    @PreAuthorize("@ss.hasPermission('jl:template-contract:query')")
    public CommonResult<PageResult<TemplateContractRespVO>> getTemplateContractPage(@Valid TemplateContractPageReqVO pageVO, @Valid TemplateContractPageOrder orderV0) {
        PageResult<TemplateContract> pageResult = templateContractService.getTemplateContractPage(pageVO, orderV0);
        return success(templateContractMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出合同模板 Excel")
    @PreAuthorize("@ss.hasPermission('jl:template-contract:export')")
    @OperateLog(type = EXPORT)
    public void exportTemplateContractExcel(@Valid TemplateContractExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<TemplateContract> list = templateContractService.getTemplateContractList(exportReqVO);
        // 导出 Excel
        List<TemplateContractExcelVO> excelData = templateContractMapper.toExcelList(list);
        ExcelUtils.write(response, "合同模板.xls", "数据", TemplateContractExcelVO.class, excelData);
    }

}
