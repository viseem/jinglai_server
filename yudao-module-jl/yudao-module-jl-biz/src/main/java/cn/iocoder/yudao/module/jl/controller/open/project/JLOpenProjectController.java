package cn.iocoder.yudao.module.jl.controller.open.project;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.controller.admin.projectquotation.vo.ProjectQuotationRespVO;
import cn.iocoder.yudao.module.jl.entity.project.Project;
import cn.iocoder.yudao.module.jl.entity.project.ProjectCategoryOnly;
import cn.iocoder.yudao.module.jl.entity.projectquotation.ProjectQuotation;
import cn.iocoder.yudao.module.jl.mapper.project.ProjectMapper;
import cn.iocoder.yudao.module.jl.mapper.projectquotation.ProjectQuotationMapper;
import cn.iocoder.yudao.module.jl.service.project.ProjectCategoryService;
import cn.iocoder.yudao.module.jl.service.project.ProjectService;
import cn.iocoder.yudao.module.jl.service.projectquotation.ProjectQuotationService;
import cn.iocoder.yudao.module.system.controller.admin.util.vo.UtilStoreGetReqVO;
import cn.iocoder.yudao.module.system.service.util.UtilServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/project")
@Validated
public class JLOpenProjectController {
    @Resource
    private ProjectService projectService;

    @Resource
    private ProjectMapper projectMapper;


    @Resource
    private UtilServiceImpl utilService;


    @PostMapping("/get")
    @Operation(summary = "通过 ID 获得项目")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<ProjectRespVO> getProjectQuotation(@RequestParam("id") Long id, @RequestBody @Valid UtilStoreGetReqVO pageVO) {
        utilService.validStoreWithException(pageVO);
        Optional<Project> project = projectService.getProject(id);
        return success(project.map(projectMapper::toDto).orElseThrow(() -> exception(PROJECT_QUOTATION_NOT_EXISTS)));
    }


}
