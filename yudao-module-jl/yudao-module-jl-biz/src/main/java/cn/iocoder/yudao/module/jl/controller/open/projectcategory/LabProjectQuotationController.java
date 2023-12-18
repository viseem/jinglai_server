package cn.iocoder.yudao.module.jl.controller.open.projectcategory;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.excel.core.util.excelhandler.CustomExcelCellWriterHandler;
import cn.iocoder.yudao.framework.excel.core.util.excelstrategy.JLCustomMergeStrategy;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.jl.controller.admin.projectquotation.ProjectQuotationUpdatePlanReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.projectquotation.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectquotation.ProjectQuotation;
import cn.iocoder.yudao.module.jl.mapper.projectquotation.ProjectQuotationMapper;
import cn.iocoder.yudao.module.jl.repository.project.ProjectRepository;
import cn.iocoder.yudao.module.jl.service.projectquotation.ProjectQuotationService;
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
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.PROJECT_QUOTATION_NOT_EXISTS;

@Tag(name = "管理后台 - 项目报价")
@RestController
@RequestMapping("/lab/project-quotation")
@Validated
public class LabProjectQuotationController {

    @Resource
    private ProjectQuotationService projectQuotationService;

    @Resource
    private ProjectQuotationMapper projectQuotationMapper;


    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得项目报价")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<ProjectQuotationRespVO> getProjectQuotation(@RequestParam("id") Long id) {
            Optional<ProjectQuotation> projectQuotation = projectQuotationService.getProjectQuotation(id);
        return success(projectQuotation.map(projectQuotationMapper::toDto).orElseThrow(() -> exception(PROJECT_QUOTATION_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得项目报价列表")
    public CommonResult<PageResult<ProjectQuotationRespVO>> getProjectQuotationPage(@Valid ProjectQuotationPageReqVO pageVO, @Valid ProjectQuotationPageOrder orderV0) {
        PageResult<ProjectQuotation> pageResult = projectQuotationService.getProjectQuotationPage(pageVO, orderV0);
        return success(projectQuotationMapper.toPage(pageResult));
    }

}
