package cn.iocoder.yudao.module.jl.controller.admin.salesgroupmember;

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

import cn.iocoder.yudao.module.jl.controller.admin.salesgroupmember.vo.*;
import cn.iocoder.yudao.module.jl.entity.salesgroupmember.SalesGroupMember;
import cn.iocoder.yudao.module.jl.mapper.salesgroupmember.SalesGroupMemberMapper;
import cn.iocoder.yudao.module.jl.service.salesgroupmember.SalesGroupMemberService;

@Tag(name = "管理后台 - 销售分组成员")
@RestController
@RequestMapping("/jl/sales-group-member")
@Validated
public class SalesGroupMemberController {

    @Resource
    private SalesGroupMemberService salesGroupMemberService;

    @Resource
    private SalesGroupMemberMapper salesGroupMemberMapper;

    @PostMapping("/create")
    @Operation(summary = "创建销售分组成员")
    @PreAuthorize("@ss.hasPermission('jl:sales-group-member:create')")
    public CommonResult<Long> createSalesGroupMember(@Valid @RequestBody SalesGroupMemberCreateReqVO createReqVO) {
        return success(salesGroupMemberService.createSalesGroupMember(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新销售分组成员")
    @PreAuthorize("@ss.hasPermission('jl:sales-group-member:update')")
    public CommonResult<Boolean> updateSalesGroupMember(@Valid @RequestBody SalesGroupMemberUpdateReqVO updateReqVO) {
        salesGroupMemberService.updateSalesGroupMember(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除销售分组成员")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:sales-group-member:delete')")
    public CommonResult<Boolean> deleteSalesGroupMember(@RequestParam("id") Long id) {
        salesGroupMemberService.deleteSalesGroupMember(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得销售分组成员")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:sales-group-member:query')")
    public CommonResult<SalesGroupMemberRespVO> getSalesGroupMember(@RequestParam("id") Long id) {
            Optional<SalesGroupMember> salesGroupMember = salesGroupMemberService.getSalesGroupMember(id);
        return success(salesGroupMember.map(salesGroupMemberMapper::toDto).orElseThrow(() -> exception(SALES_GROUP_MEMBER_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得销售分组成员列表")
    @PreAuthorize("@ss.hasPermission('jl:sales-group-member:query')")
    public CommonResult<PageResult<SalesGroupMemberRespVO>> getSalesGroupMemberPage(@Valid SalesGroupMemberPageReqVO pageVO, @Valid SalesGroupMemberPageOrder orderV0) {
        PageResult<SalesGroupMember> pageResult = salesGroupMemberService.getSalesGroupMemberPage(pageVO, orderV0);
        return success(salesGroupMemberMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出销售分组成员 Excel")
    @PreAuthorize("@ss.hasPermission('jl:sales-group-member:export')")
    @OperateLog(type = EXPORT)
    public void exportSalesGroupMemberExcel(@Valid SalesGroupMemberExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<SalesGroupMember> list = salesGroupMemberService.getSalesGroupMemberList(exportReqVO);
        // 导出 Excel
        List<SalesGroupMemberExcelVO> excelData = salesGroupMemberMapper.toExcelList(list);
        ExcelUtils.write(response, "销售分组成员.xls", "数据", SalesGroupMemberExcelVO.class, excelData);
    }

}
