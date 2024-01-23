package cn.iocoder.yudao.module.jl.controller.admin.crmsubjectgroup;

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

import cn.iocoder.yudao.module.jl.controller.admin.crmsubjectgroup.vo.*;
import cn.iocoder.yudao.module.jl.entity.crmsubjectgroup.CrmSubjectGroup;
import cn.iocoder.yudao.module.jl.mapper.crmsubjectgroup.CrmSubjectGroupMapper;
import cn.iocoder.yudao.module.jl.service.crmsubjectgroup.CrmSubjectGroupService;

@Tag(name = "管理后台 - 客户课题组")
@RestController
@RequestMapping("/jl/crm-subject-group")
@Validated
public class CrmSubjectGroupController {

    @Resource
    private CrmSubjectGroupService crmSubjectGroupService;

    @Resource
    private CrmSubjectGroupMapper crmSubjectGroupMapper;

    @PostMapping("/create")
    @Operation(summary = "创建客户课题组")
    @PreAuthorize("@ss.hasPermission('jl:crm-subject-group:create')")
    public CommonResult<Long> createCrmSubjectGroup(@Valid @RequestBody CrmSubjectGroupCreateReqVO createReqVO) {
        Long crmSubjectGroup = crmSubjectGroupService.createCrmSubjectGroup(createReqVO);
        if(crmSubjectGroup==-1){
            return success(crmSubjectGroup, "课题组名称已存在");
        }
        return success(crmSubjectGroup);
    }

    @PutMapping("/update")
    @Operation(summary = "更新客户课题组")
    @PreAuthorize("@ss.hasPermission('jl:crm-subject-group:update')")
    public CommonResult<Boolean> updateCrmSubjectGroup(@Valid @RequestBody CrmSubjectGroupUpdateReqVO updateReqVO) {
        crmSubjectGroupService.updateCrmSubjectGroup(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除客户课题组")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:crm-subject-group:delete')")
    public CommonResult<Boolean> deleteCrmSubjectGroup(@RequestParam("id") Long id) {
        crmSubjectGroupService.deleteCrmSubjectGroup(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得客户课题组")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:crm-subject-group:query')")
    public CommonResult<CrmSubjectGroupRespVO> getCrmSubjectGroup(@RequestParam("id") Long id) {
            Optional<CrmSubjectGroup> crmSubjectGroup = crmSubjectGroupService.getCrmSubjectGroup(id);
        return success(crmSubjectGroup.map(crmSubjectGroupMapper::toDto).orElseThrow(() -> exception(CRM_SUBJECT_GROUP_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得客户课题组列表")
    @PreAuthorize("@ss.hasPermission('jl:crm-subject-group:query')")
    public CommonResult<PageResult<CrmSubjectGroupRespVO>> getCrmSubjectGroupPage(@Valid CrmSubjectGroupPageReqVO pageVO, @Valid CrmSubjectGroupPageOrder orderV0) {
        PageResult<CrmSubjectGroup> pageResult = crmSubjectGroupService.getCrmSubjectGroupPage(pageVO, orderV0);
        return success(crmSubjectGroupMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出客户课题组 Excel")
    @PreAuthorize("@ss.hasPermission('jl:crm-subject-group:export')")
    @OperateLog(type = EXPORT)
    public void exportCrmSubjectGroupExcel(@Valid CrmSubjectGroupExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<CrmSubjectGroup> list = crmSubjectGroupService.getCrmSubjectGroupList(exportReqVO);
        // 导出 Excel
        List<CrmSubjectGroupExcelVO> excelData = crmSubjectGroupMapper.toExcelList(list);
        ExcelUtils.write(response, "客户课题组.xls", "数据", CrmSubjectGroupExcelVO.class, excelData);
    }

}
