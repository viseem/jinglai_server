package cn.iocoder.yudao.module.jl.controller.admin.dept;

import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.access.prepost.PreAuthorize;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Operation;

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

import cn.iocoder.yudao.module.jl.controller.admin.dept.vo.*;
import cn.iocoder.yudao.module.jl.entity.dept.Dept;
import cn.iocoder.yudao.module.jl.mapper.dept.XDeptMapper;
import cn.iocoder.yudao.module.jl.service.dept.XDeptService;

@Tag(name = "管理后台 - 部门")
@RestController
@RequestMapping("/jl/dept")
@Validated
public class XDeptController {

    @Resource
    private XDeptService deptService;

    @Resource
    private XDeptMapper deptMapper;

    @PostMapping("/create")
    @Operation(summary = "创建部门")
    @PreAuthorize("@ss.hasPermission('jl:dept:create')")
    public CommonResult<Long> createDept(@Valid @RequestBody DeptCreateReqVO createReqVO) {
        return success(deptService.createDept(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新部门")
    @PreAuthorize("@ss.hasPermission('jl:dept:update')")
    public CommonResult<Boolean> updateDept(@Valid @RequestBody DeptUpdateReqVO updateReqVO) {
        deptService.updateDept(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除部门")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:dept:delete')")
    public CommonResult<Boolean> deleteDept(@RequestParam("id") Long id) {
        deptService.deleteDept(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得部门")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:dept:query')")
    public CommonResult<DeptRespVO> getDept(@RequestParam("id") Long id) {
            Optional<Dept> dept = deptService.getDept(id);
        return success(dept.map(deptMapper::toDto).orElseThrow(() -> exception(DEPT_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得部门列表")
    @PreAuthorize("@ss.hasPermission('jl:dept:query')")
    public CommonResult<PageResult<DeptRespVO>> getDeptPage(@Valid DeptPageReqVO pageVO, @Valid DeptPageOrder orderV0) {
        PageResult<Dept> pageResult = deptService.getDeptPage(pageVO, orderV0);
        return success(deptMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出部门 Excel")
    @PreAuthorize("@ss.hasPermission('jl:dept:export')")
    @OperateLog(type = EXPORT)
    public void exportDeptExcel(@Valid DeptExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<Dept> list = deptService.getDeptList(exportReqVO);
        // 导出 Excel
        List<DeptExcelVO> excelData = deptMapper.toExcelList(list);
        ExcelUtils.write(response, "部门.xls", "数据", DeptExcelVO.class, excelData);
    }

}
