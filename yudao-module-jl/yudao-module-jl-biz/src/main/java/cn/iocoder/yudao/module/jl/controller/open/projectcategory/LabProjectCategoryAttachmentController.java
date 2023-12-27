package cn.iocoder.yudao.module.jl.controller.open.projectcategory;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo.*;
import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryAttachment;
import cn.iocoder.yudao.module.jl.mapper.projectcategory.ProjectCategoryAttachmentMapper;
import cn.iocoder.yudao.module.jl.service.projectcategory.ProjectCategoryAttachmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.operatelog.core.enums.OperateTypeEnum.EXPORT;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.PROJECT_CATEGORY_ATTACHMENT_NOT_EXISTS;

@Tag(name = "管理后台 - 项目实验名目的附件")
@RestController
@RequestMapping("/lab/exp-attachment")
@Validated
public class LabProjectCategoryAttachmentController {

    @Resource
    private ProjectCategoryAttachmentService projectCategoryAttachmentService;

    @Resource
    private ProjectCategoryAttachmentMapper projectCategoryAttachmentMapper;

    @GetMapping("/page")
    @Operation(summary = "(分页)获得项目实验名目的附件列表")
    @PermitAll
    public CommonResult<PageResult<ProjectCategoryAttachmentRespVO>> getProjectCategoryAttachmentPage(@Valid ProjectCategoryAttachmentPageReqVO pageVO, @Valid ProjectCategoryAttachmentPageOrder orderV0) {
        PageResult<ProjectCategoryAttachment> pageResult = projectCategoryAttachmentService.getProjectCategoryAttachmentPage(pageVO, orderV0);
        return success(projectCategoryAttachmentMapper.toPage(pageResult));
    }

}
