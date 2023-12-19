package cn.iocoder.yudao.module.jl.controller.open.projectcategory;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.jl.controller.admin.laboratory.vo.*;
import cn.iocoder.yudao.module.jl.entity.laboratory.LaboratoryUser;
import cn.iocoder.yudao.module.jl.mapper.laboratory.LaboratoryUserMapper;
import cn.iocoder.yudao.module.jl.service.laboratory.LaboratoryUserService;
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
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.LABORATORY_USER_NOT_EXISTS;

@Tag(name = "管理后台 - 实验室人员")
@RestController
@RequestMapping("/lab/laboratory-user")
@Validated
public class LabLaboratoryUserController {

    @Resource
    private LaboratoryUserService laboratoryUserService;

    @Resource
    private LaboratoryUserMapper laboratoryUserMapper;

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得实验室人员")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<LaboratoryUserRespVO> getLaboratoryUser(@RequestParam("id") Long id) {
            Optional<LaboratoryUser> laboratoryUser = laboratoryUserService.getLaboratoryUser(id);
        return success(laboratoryUser.map(laboratoryUserMapper::toDto).orElseThrow(() -> exception(LABORATORY_USER_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得实验室人员列表")
    public CommonResult<PageResult<LaboratoryUserRespVO>> getLaboratoryUserPage(@Valid LaboratoryUserPageReqVO pageVO, @Valid LaboratoryUserPageOrder orderV0) {
        PageResult<LaboratoryUser> pageResult = laboratoryUserService.getLaboratoryUserPage(pageVO, orderV0);
        return success(laboratoryUserMapper.toPage(pageResult));
    }



}
