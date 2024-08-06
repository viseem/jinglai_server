package cn.iocoder.yudao.module.jl.controller.open.project;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.jl.controller.admin.projectoutlog.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectoutlog.ProjectOutLog;
import cn.iocoder.yudao.module.jl.mapper.projectoutlog.ProjectOutLogMapper;
import cn.iocoder.yudao.module.jl.service.projectoutlog.ProjectOutLogService;
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
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.PROJECT_OUT_LOG_NOT_EXISTS;

@Tag(name = "开发后台 - example 空")
@RestController
@RequestMapping("project-out-log")
@Validated
public class JLOpenProjectOutLogController {

    @Resource
    private ProjectOutLogService projectOutLogService;

    @Resource
    private ProjectOutLogMapper projectOutLogMapper;


    @PostMapping("/get")
    @Operation(summary = "通过 ID 获得example 空")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<ProjectOutLogRespVO> getProjectOutLog(@RequestParam("id") Long id) {
            Optional<ProjectOutLog> projectOutLog = projectOutLogService.getProjectOutLog(id);
        return success(projectOutLog.map(projectOutLogMapper::toDto).orElseThrow(() -> exception(PROJECT_OUT_LOG_NOT_EXISTS)));
    }

    @PutMapping("/update")
    @Operation(summary = "更新example 空")
    public CommonResult<Boolean> updateProjectOutLog(@Valid @RequestBody ProjectOutLogUpdateReqVO updateReqVO) {
        projectOutLogService.updateProjectOutLog(updateReqVO);
        return success(true);
    }

}
