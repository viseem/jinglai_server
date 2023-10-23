package cn.iocoder.yudao.module.jl.controller.admin.subjectgroup;

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

import cn.iocoder.yudao.module.jl.controller.admin.subjectgroup.vo.*;
import cn.iocoder.yudao.module.jl.entity.subjectgroup.SubjectGroup;
import cn.iocoder.yudao.module.jl.mapper.subjectgroup.SubjectGroupMapper;
import cn.iocoder.yudao.module.jl.service.subjectgroup.SubjectGroupService;

@Tag(name = "管理后台 - 专题小组")
@RestController
@RequestMapping("/jl/subject-group")
@Validated
public class SubjectGroupController {

    @Resource
    private SubjectGroupService subjectGroupService;

    @Resource
    private SubjectGroupMapper subjectGroupMapper;

    @PostMapping("/create")
    @Operation(summary = "创建专题小组")
    @PreAuthorize("@ss.hasPermission('jl:subject-group:create')")
    public CommonResult<Long> createSubjectGroup(@Valid @RequestBody SubjectGroupCreateReqVO createReqVO) {
        return success(subjectGroupService.createSubjectGroup(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新专题小组")
    @PreAuthorize("@ss.hasPermission('jl:subject-group:update')")
    public CommonResult<Boolean> updateSubjectGroup(@Valid @RequestBody SubjectGroupUpdateReqVO updateReqVO) {
        subjectGroupService.updateSubjectGroup(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除专题小组")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:subject-group:delete')")
    public CommonResult<Boolean> deleteSubjectGroup(@RequestParam("id") Long id) {
        subjectGroupService.deleteSubjectGroup(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得专题小组")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:subject-group:query')")
    public CommonResult<SubjectGroupRespVO> getSubjectGroup(@RequestParam("id") Long id) {
            Optional<SubjectGroup> subjectGroup = subjectGroupService.getSubjectGroup(id);
        return success(subjectGroup.map(subjectGroupMapper::toDto).orElseThrow(() -> exception(SUBJECT_GROUP_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得专题小组列表")
    @PreAuthorize("@ss.hasPermission('jl:subject-group:query')")
    public CommonResult<PageResult<SubjectGroupRespVO>> getSubjectGroupPage(@Valid SubjectGroupPageReqVO pageVO, @Valid SubjectGroupPageOrder orderV0) {
        PageResult<SubjectGroup> pageResult = subjectGroupService.getSubjectGroupPage(pageVO, orderV0);
        return success(subjectGroupMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出专题小组 Excel")
    @PreAuthorize("@ss.hasPermission('jl:subject-group:export')")
    @OperateLog(type = EXPORT)
    public void exportSubjectGroupExcel(@Valid SubjectGroupExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<SubjectGroup> list = subjectGroupService.getSubjectGroupList(exportReqVO);
        // 导出 Excel
        List<SubjectGroupExcelVO> excelData = subjectGroupMapper.toExcelList(list);
        ExcelUtils.write(response, "专题小组.xls", "数据", SubjectGroupExcelVO.class, excelData);
    }

}
