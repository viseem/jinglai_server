package cn.iocoder.yudao.module.jl.controller.open.quotation;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.ProjectCategoryPageOrder;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.ProjectCategoryPageReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.ProjectSupplyAndChargeReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.ProjectSupplyAndChargeRespVO;
import cn.iocoder.yudao.module.jl.controller.admin.projectquotation.vo.ProjectQuotationRespVO;
import cn.iocoder.yudao.module.jl.entity.project.ProjectCategory;
import cn.iocoder.yudao.module.jl.entity.projectquotation.ProjectQuotation;
import cn.iocoder.yudao.module.jl.mapper.projectquotation.ProjectQuotationMapper;
import cn.iocoder.yudao.module.jl.service.project.ProjectCategoryService;
import cn.iocoder.yudao.module.jl.service.project.ProjectService;
import cn.iocoder.yudao.module.jl.service.projectquotation.ProjectQuotationService;
import cn.iocoder.yudao.module.system.controller.admin.util.vo.UtilStoreGetReqVO;
import cn.iocoder.yudao.module.system.service.util.UtilServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.PROJECT_QUOTATION_NOT_EXISTS;

@Tag(name = "管理后台 - 报价开放接口")
@RestController
@RequestMapping("/quotation")
@Validated
public class JLOpenQuotationController {
    @Resource
    private ProjectQuotationService projectQuotationService;

    @Resource
    private ProjectQuotationMapper projectQuotationMapper;

    @Resource
    private ProjectService projectService;

    @Resource
    private UtilServiceImpl utilService;

    @Resource
    private ProjectCategoryService projectCategoryService;


    @PostMapping("/get")
    @Operation(summary = "通过 ID 获得项目报价")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<ProjectQuotationRespVO> getProjectQuotation(@RequestParam("id") Long id,@RequestBody @Valid UtilStoreGetReqVO pageVO) {
        utilService.validStoreWithException(pageVO);
        Optional<ProjectQuotation> projectQuotation = projectQuotationService.getProjectQuotation(id);
        return success(projectQuotation.map(projectQuotationMapper::toDto).orElseThrow(() -> exception(PROJECT_QUOTATION_NOT_EXISTS)));
    }

    @PostMapping("/category-page")
    @Operation(summary = "通过 ID 获得项目报价")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<PageResult<ProjectCategory>> geProjectCategoryPage(@RequestBody @Valid ProjectCategoryPageReqVO pageVO) {
        UtilStoreGetReqVO storeGetReqVO = new UtilStoreGetReqVO();
        storeGetReqVO.setStoreId(pageVO.getStoreId());
        storeGetReqVO.setStorePwd(pageVO.getStorePwd());
        utilService.validStoreWithException(storeGetReqVO);
        PageResult<ProjectCategory> projectCategoryPage = projectCategoryService.getProjectCategoryPage(pageVO, new ProjectCategoryPageOrder());
        return success(projectCategoryPage);
    }

    @PostMapping("/supply-and-charge")
    @Operation(summary = "项目物资")
    public CommonResult<ProjectSupplyAndChargeRespVO> getProjectPage(@RequestBody @Valid ProjectSupplyAndChargeReqVO pageVO) {
        System.out.println("page----"+pageVO);
        utilService.validStoreWithException(pageVO);

        ProjectSupplyAndChargeRespVO projectSupplyAndCharge = projectService.getProjectSupplyAndCharge(pageVO);
        return success(projectSupplyAndCharge);
    }

}
