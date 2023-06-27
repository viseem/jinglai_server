package cn.iocoder.yudao.module.jl.controller.admin.laboratory;

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

import cn.iocoder.yudao.module.jl.controller.admin.laboratory.vo.*;
import cn.iocoder.yudao.module.jl.entity.laboratory.LaboratoryUser;
import cn.iocoder.yudao.module.jl.mapper.laboratory.LaboratoryUserMapper;
import cn.iocoder.yudao.module.jl.service.laboratory.LaboratoryUserService;

@Tag(name = "管理后台 - 实验室人员")
@RestController
@RequestMapping("/jl/laboratory-user")
@Validated
public class LaboratoryUserController {

    @Resource
    private LaboratoryUserService laboratoryUserService;

    @Resource
    private LaboratoryUserMapper laboratoryUserMapper;

    @PostMapping("/create")
    @Operation(summary = "创建实验室人员")
    @PreAuthorize("@ss.hasPermission('jl:laboratory-user:create')")
    public CommonResult<Long> createLaboratoryUser(@Valid @RequestBody LaboratoryUserCreateReqVO createReqVO) {
        return success(laboratoryUserService.createLaboratoryUser(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新实验室人员")
    @PreAuthorize("@ss.hasPermission('jl:laboratory-user:update')")
    public CommonResult<Boolean> updateLaboratoryUser(@Valid @RequestBody LaboratoryUserUpdateReqVO updateReqVO) {
        laboratoryUserService.updateLaboratoryUser(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除实验室人员")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:laboratory-user:delete')")
    public CommonResult<Boolean> deleteLaboratoryUser(@RequestParam("id") Long id) {
        laboratoryUserService.deleteLaboratoryUser(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得实验室人员")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:laboratory-user:query')")
    public CommonResult<LaboratoryUserRespVO> getLaboratoryUser(@RequestParam("id") Long id) {
            Optional<LaboratoryUser> laboratoryUser = laboratoryUserService.getLaboratoryUser(id);
        return success(laboratoryUser.map(laboratoryUserMapper::toDto).orElseThrow(() -> exception(LABORATORY_USER_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得实验室人员列表")
    @PreAuthorize("@ss.hasPermission('jl:laboratory-user:query')")
    public CommonResult<PageResult<LaboratoryUserRespVO>> getLaboratoryUserPage(@Valid LaboratoryUserPageReqVO pageVO, @Valid LaboratoryUserPageOrder orderV0) {
        PageResult<LaboratoryUser> pageResult = laboratoryUserService.getLaboratoryUserPage(pageVO, orderV0);
        return success(laboratoryUserMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出实验室人员 Excel")
    @PreAuthorize("@ss.hasPermission('jl:laboratory-user:export')")
    @OperateLog(type = EXPORT)
    public void exportLaboratoryUserExcel(@Valid LaboratoryUserExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<LaboratoryUser> list = laboratoryUserService.getLaboratoryUserList(exportReqVO);
        // 导出 Excel
        List<LaboratoryUserExcelVO> excelData = laboratoryUserMapper.toExcelList(list);
        ExcelUtils.write(response, "实验室人员.xls", "数据", LaboratoryUserExcelVO.class, excelData);
    }

}
