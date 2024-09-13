package cn.iocoder.yudao.module.jl.controller.admin.crm;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.jl.controller.admin.crm.vo.*;
import cn.iocoder.yudao.module.jl.entity.crm.Institution;
import cn.iocoder.yudao.module.jl.mapper.crm.InstitutionMapper;
import cn.iocoder.yudao.module.jl.service.crm.InstitutionServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.INSTITUTION_NOT_EXISTS;

@Tag(name = "管理后台 - 机构/公司")
@RestController
@RequestMapping("/jl/institution")
@Validated
public class InstitutionController {

    @Resource
    private InstitutionServiceImpl institutionService;

    @Resource
    private InstitutionMapper institutionMapper;

    @PostMapping("/create")
    @Operation(summary = "创建机构/公司")
    @PreAuthorize("@ss.hasPermission('jl:institution:create')")
    public CommonResult<Long> createInstitution(@Valid @RequestBody InstitutionCreateReqVO createReqVO) {

        Long institution = institutionService.createInstitution(createReqVO);
        String msg="";
        if(institution==0){
            msg="该机构已存在";
        }

        return success(institution,msg);
    }

    @PutMapping("/update")
    @Operation(summary = "更新机构/公司")
    @PreAuthorize("@ss.hasPermission('jl:institution:update')")
    public CommonResult<Boolean> updateInstitution(@Valid @RequestBody InstitutionUpdateReqVO updateReqVO) {
        institutionService.updateInstitution(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除机构/公司")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:institution:delete')")
    public CommonResult<Boolean> deleteInstitution(@RequestParam("id") Long id) {
        institutionService.deleteInstitution(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得机构/公司")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:institution:query')")
    public CommonResult<InstitutionRespVO> getInstitution(@RequestParam("id") Long id) {
            Optional<Institution> institution = institutionService.getInstitution(id);
        return success(institution.map(institutionMapper::toDto).orElseThrow(() -> exception(INSTITUTION_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得机构/公司列表")
    @PreAuthorize("@ss.hasPermission('jl:institution:query')")
    public CommonResult<PageResult<InstitutionRespVO>> getInstitutionPage(@Valid InstitutionPageReqVO pageVO, @Valid InstitutionPageOrder orderV0) {
        PageResult<Institution> pageResult = institutionService.getInstitutionPage(pageVO, orderV0);
        return success(institutionMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出机构/公司 Excel")
    @PreAuthorize("@ss.hasPermission('jl:institution:export')")
    @OperateLog(type = EXPORT)
    public void exportInstitutionExcel(@Valid InstitutionExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<Institution> list = institutionService.getInstitutionList(exportReqVO);
        // 导出 Excel
        List<InstitutionExcelVO> excelData = institutionMapper.toExcelList(list);
        ExcelUtils.write(response, "机构/公司.xls", "数据", InstitutionExcelVO.class, excelData);
    }

    //excel导入
    @PostMapping("/import-excel")
    @Operation(summary = "导入用户")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    public CommonResult<InstitutionImportRespVO> importExcel(@RequestParam("file") MultipartFile file,
                                                          @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
        List<InstitutionImportVO> list = ExcelUtils.read(file, InstitutionImportVO.class);
        return success(institutionService.importList(list, updateSupport));
    }

    @PostMapping("/import-excel-tax-number")
    @Operation(summary = "导入用户")
    @Parameters({
            @Parameter(name = "file", description = "Excel 文件", required = true),
            @Parameter(name = "updateSupport", description = "是否支持更新，默认为 false", example = "true")
    })
    public CommonResult<InstitutionImportRespVO> importExcelTaxNumber(@RequestParam("file") MultipartFile file,
                                                             @RequestParam(value = "updateSupport", required = false, defaultValue = "false") Boolean updateSupport) throws Exception {
        List<InstitutionImportTaxNumberVO> list = ExcelUtils.read(file, InstitutionImportTaxNumberVO.class);
        return success(institutionService.importListTaxNumber(list, updateSupport));
    }
}
