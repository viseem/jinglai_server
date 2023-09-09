package cn.iocoder.yudao.module.jl.controller.admin.projectperson;

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

import cn.iocoder.yudao.module.jl.controller.admin.projectperson.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectperson.ProjectPerson;
import cn.iocoder.yudao.module.jl.mapper.projectperson.ProjectPersonMapper;
import cn.iocoder.yudao.module.jl.service.projectperson.ProjectPersonService;

@Tag(name = "管理后台 - 项目人员")
@RestController
@RequestMapping("/jl/project-person")
@Validated
public class ProjectPersonController {

    @Resource
    private ProjectPersonService projectPersonService;

    @Resource
    private ProjectPersonMapper projectPersonMapper;

    @PostMapping("/create")
    @Operation(summary = "创建项目人员")
    @PreAuthorize("@ss.hasPermission('jl:project-person:create')")
    public CommonResult<Long> createProjectPerson(@Valid @RequestBody ProjectPersonCreateReqVO createReqVO) {
        return success(projectPersonService.createProjectPerson(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新项目人员")
    @PreAuthorize("@ss.hasPermission('jl:project-person:update')")
    public CommonResult<Boolean> updateProjectPerson(@Valid @RequestBody ProjectPersonUpdateReqVO updateReqVO) {
        projectPersonService.updateProjectPerson(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除项目人员")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:project-person:delete')")
    public CommonResult<Boolean> deleteProjectPerson(@RequestParam("id") Long id) {
        projectPersonService.deleteProjectPerson(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得项目人员")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:project-person:query')")
    public CommonResult<ProjectPersonRespVO> getProjectPerson(@RequestParam("id") Long id) {
            Optional<ProjectPerson> projectPerson = projectPersonService.getProjectPerson(id);
        return success(projectPerson.map(projectPersonMapper::toDto).orElseThrow(() -> exception(PROJECT_PERSON_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得项目人员列表")
    @PreAuthorize("@ss.hasPermission('jl:project-person:query')")
    public CommonResult<PageResult<ProjectPersonRespVO>> getProjectPersonPage(@Valid ProjectPersonPageReqVO pageVO, @Valid ProjectPersonPageOrder orderV0) {
        PageResult<ProjectPerson> pageResult = projectPersonService.getProjectPersonPage(pageVO, orderV0);
        return success(projectPersonMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出项目人员 Excel")
    @PreAuthorize("@ss.hasPermission('jl:project-person:export')")
    @OperateLog(type = EXPORT)
    public void exportProjectPersonExcel(@Valid ProjectPersonExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<ProjectPerson> list = projectPersonService.getProjectPersonList(exportReqVO);
        // 导出 Excel
        List<ProjectPersonExcelVO> excelData = projectPersonMapper.toExcelList(list);
        ExcelUtils.write(response, "项目人员.xls", "数据", ProjectPersonExcelVO.class, excelData);
    }

}
