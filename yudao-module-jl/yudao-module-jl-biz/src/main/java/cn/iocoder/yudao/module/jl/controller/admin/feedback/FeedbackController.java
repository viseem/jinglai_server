package cn.iocoder.yudao.module.jl.controller.admin.feedback;

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

import cn.iocoder.yudao.module.jl.controller.admin.feedback.vo.*;
import cn.iocoder.yudao.module.jl.entity.feedback.Feedback;
import cn.iocoder.yudao.module.jl.mapper.feedback.FeedbackMapper;
import cn.iocoder.yudao.module.jl.service.feedback.FeedbackService;

@Tag(name = "管理后台 - 晶莱反馈")
@RestController
@RequestMapping("/jl/feedback")
@Validated
public class FeedbackController {

    @Resource
    private FeedbackService feedbackService;

    @Resource
    private FeedbackMapper feedbackMapper;

    @PostMapping("/create")
    @Operation(summary = "创建晶莱反馈")
    @PreAuthorize("@ss.hasPermission('jl:feedback:create')")
    public CommonResult<Long> createFeedback(@Valid @RequestBody FeedbackCreateReqVO createReqVO) {
        return success(feedbackService.createFeedback(createReqVO));
    }

    @PutMapping("/update")
    @Operation(summary = "更新晶莱反馈")
    @PreAuthorize("@ss.hasPermission('jl:feedback:update')")
    public CommonResult<Boolean> updateFeedback(@Valid @RequestBody FeedbackUpdateReqVO updateReqVO) {
        feedbackService.updateFeedback(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "通过 ID 删除晶莱反馈")
    @Parameter(name = "id", description = "编号", required = true)
    @PreAuthorize("@ss.hasPermission('jl:feedback:delete')")
    public CommonResult<Boolean> deleteFeedback(@RequestParam("id") Long id) {
        feedbackService.deleteFeedback(id);
        return success(true);
    }

    @GetMapping("/get")
    @Operation(summary = "通过 ID 获得晶莱反馈")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:feedback:query')")
    public CommonResult<FeedbackRespVO> getFeedback(@RequestParam("id") Long id) {
            Optional<Feedback> feedback = feedbackService.getFeedback(id);
        return success(feedback.map(feedbackMapper::toDto).orElseThrow(() -> exception(FEEDBACK_NOT_EXISTS)));
    }

    @GetMapping("/page")
    @Operation(summary = "(分页)获得晶莱反馈列表")
    @PreAuthorize("@ss.hasPermission('jl:feedback:query')")
    public CommonResult<PageResult<FeedbackRespVO>> getFeedbackPage(@Valid FeedbackPageReqVO pageVO, @Valid FeedbackPageOrder orderV0) {
        PageResult<Feedback> pageResult = feedbackService.getFeedbackPage(pageVO, orderV0);
        return success(feedbackMapper.toPage(pageResult));
    }

    @GetMapping("/export-excel")
    @Operation(summary = "导出晶莱反馈 Excel")
    @PreAuthorize("@ss.hasPermission('jl:feedback:export')")
    @OperateLog(type = EXPORT)
    public void exportFeedbackExcel(@Valid FeedbackExportReqVO exportReqVO, HttpServletResponse response) throws IOException {
        List<Feedback> list = feedbackService.getFeedbackList(exportReqVO);
        // 导出 Excel
        List<FeedbackExcelVO> excelData = feedbackMapper.toExcelList(list);
        ExcelUtils.write(response, "晶莱反馈.xls", "数据", FeedbackExcelVO.class, excelData);
    }

}
