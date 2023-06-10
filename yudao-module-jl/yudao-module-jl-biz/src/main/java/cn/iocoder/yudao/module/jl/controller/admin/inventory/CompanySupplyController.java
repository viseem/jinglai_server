package cn.iocoder.yudao.module.jl.controller.admin.inventory;

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

import cn.iocoder.yudao.module.jl.controller.admin.inventory.vo.*;
import cn.iocoder.yudao.module.jl.entity.inventory.CompanySupply;
import cn.iocoder.yudao.module.jl.mapper.inventory.CompanySupplyMapper;
import cn.iocoder.yudao.module.jl.service.inventory.CompanySupplyService;

@Tag(name = "管理后台 - 公司实验物资库存")
@RestController
@RequestMapping("/jl/company-supply")
@Validated
public class CompanySupplyController {

    @Resource
    private CompanySupplyService companySupplyService;

    @Resource
    private CompanySupplyMapper companySupplyMapper;

    @PostMapping("/create")
    @Operation(summary = "创建公司实验物资库存")
    @PreAuthorize("@ss.hasPermission('jl:company-supply:create')")
    public CommonResult<Long> createCompanySupply(@Valid @RequestBody CompanySupplyCreateReqVO createReqVO) {
        return success(companySupplyService.createCompanySupply(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新公司实验物资库存")
    @PreAuthorize("@ss.hasPermission('jl:company-supply:update')")
    public CommonResult<Boolean> updateCompanySupply(@Valid @RequestBody CompanySupplyUpdateReqVO updateReqVO) {
        companySupplyService.updateCompanySupply(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除公司实验物资库存")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:company-supply:delete')")
    public CommonResult<Boolean> deleteCompanySupply(@RequestParam("id") Long id) {
        companySupplyService.deleteCompanySupply(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得公司实验物资库存")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:company-supply:query')")
    public CommonResult<CompanySupplyRespVO> getCompanySupply(@RequestParam("id") Long id) {
            Optional<CompanySupply> companySupply = companySupplyService.getCompanySupply(id);
        return success(companySupply.map(companySupplyMapper::toDto).orElseThrow(() -> exception(COMPANY_SUPPLY_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得公司实验物资库存列表")
    @PreAuthorize("@ss.hasPermission('jl:company-supply:query')")
    public CommonResult<PageResult<CompanySupplyRespVO>> getCompanySupplyPage(@Valid CompanySupplyPageReqVO pageVO, @Valid CompanySupplyPageOrder orderV0) {
        PageResult<CompanySupply> pageResult = companySupplyService.getCompanySupplyPage(pageVO, orderV0);
        return success(companySupplyMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出公司实验物资库存 Excel")
    @PreAuthorize("@ss.hasPermission('jl:company-supply:export')")
    @OperateLog(type = EXPORT)
    public void exportCompanySupplyExcel(@Valid CompanySupplyExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<CompanySupply> list = companySupplyService.getCompanySupplyList(exportReqVO);
        // 导出 Excel
        List<CompanySupplyExcelVO> excelData = companySupplyMapper.toExcelList(list);
        ExcelUtils.write(response, "公司实验物资库存.xls", "数据", CompanySupplyExcelVO.class, excelData);
    }

}
