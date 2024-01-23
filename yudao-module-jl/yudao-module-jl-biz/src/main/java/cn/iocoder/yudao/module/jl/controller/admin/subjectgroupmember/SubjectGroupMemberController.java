package cn.iocoder.yudao.module.jl.controller.admin.subjectgroupmember;

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

import cn.iocoder.yudao.module.jl.controller.admin.subjectgroupmember.vo.*;
import cn.iocoder.yudao.module.jl.entity.subjectgroupmember.SubjectGroupMember;
import cn.iocoder.yudao.module.jl.mapper.subjectgroupmember.SubjectGroupMemberMapper;
import cn.iocoder.yudao.module.jl.service.subjectgroupmember.SubjectGroupMemberService;

@Tag(name = "管理后台 - 专题小组成员")
@RestController
@RequestMapping("/jl/subject-group-member")
@Validated
public class SubjectGroupMemberController {

    @Resource
    private SubjectGroupMemberService subjectGroupMemberService;

    @Resource
    private SubjectGroupMemberMapper subjectGroupMemberMapper;

    @PostMapping("/create")
    @Operation(summary = "创建专题小组成员")
    @PreAuthorize("@ss.hasPermission('jl:subject-group-member:create')")
    public CommonResult<Long> createSubjectGroupMember(@Valid @RequestBody SubjectGroupMemberCreateReqVO createReqVO) {
        return success(subjectGroupMemberService.createSubjectGroupMember(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新专题小组成员")
    @PreAuthorize("@ss.hasPermission('jl:subject-group-member:update')")
    public CommonResult<Boolean> updateSubjectGroupMember(@Valid @RequestBody SubjectGroupMemberUpdateReqVO updateReqVO) {
        subjectGroupMemberService.updateSubjectGroupMember(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除专题小组成员")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:subject-group-member:delete')")
    public CommonResult<Boolean> deleteSubjectGroupMember(@RequestParam("id") Long id) {
        subjectGroupMemberService.deleteSubjectGroupMember(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得专题小组成员")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:subject-group-member:query')")
    public CommonResult<SubjectGroupMemberRespVO> getSubjectGroupMember(@RequestParam("id") Long id) {
            Optional<SubjectGroupMember> subjectGroupMember = subjectGroupMemberService.getSubjectGroupMember(id);
        return success(subjectGroupMember.map(subjectGroupMemberMapper::toDto).orElseThrow(() -> exception(SUBJECT_GROUP_MEMBER_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得专题小组成员列表")
    @PreAuthorize("@ss.hasPermission('jl:subject-group-member:query')")
    public CommonResult<PageResult<SubjectGroupMemberRespVO>> getSubjectGroupMemberPage(@Valid SubjectGroupMemberPageReqVO pageVO, @Valid SubjectGroupMemberPageOrder orderV0) {
        PageResult<SubjectGroupMember> pageResult = subjectGroupMemberService.getSubjectGroupMemberPage(pageVO, orderV0);
        return success(subjectGroupMemberMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出专题小组成员 Excel")
    @PreAuthorize("@ss.hasPermission('jl:subject-group-member:export')")
    @OperateLog(type = EXPORT)
    public void exportSubjectGroupMemberExcel(@Valid SubjectGroupMemberExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<SubjectGroupMember> list = subjectGroupMemberService.getSubjectGroupMemberList(exportReqVO);
        // 导出 Excel
        List<SubjectGroupMemberExcelVO> excelData = subjectGroupMemberMapper.toExcelList(list);
        ExcelUtils.write(response, "专题小组成员.xls", "数据", SubjectGroupMemberExcelVO.class, excelData);
    }

}
