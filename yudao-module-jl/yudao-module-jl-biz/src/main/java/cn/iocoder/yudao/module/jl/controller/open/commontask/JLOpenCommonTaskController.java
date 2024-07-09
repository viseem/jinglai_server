package cn.iocoder.yudao.module.jl.controller.open.commontask;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.commontask.vo.CommonTaskPageOrder;
import cn.iocoder.yudao.module.jl.controller.admin.commontask.vo.CommonTaskPageReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.commontask.vo.CommonTaskRespVO;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.ProjectCategoryPageOrder;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.ProjectCategoryPageReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.ProjectSupplyAndChargeReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.ProjectSupplyAndChargeRespVO;
import cn.iocoder.yudao.module.jl.controller.admin.projectquotation.vo.ProjectQuotationRespVO;
import cn.iocoder.yudao.module.jl.entity.commontask.CommonTask;
import cn.iocoder.yudao.module.jl.entity.project.ProjectCategoryOnly;
import cn.iocoder.yudao.module.jl.entity.projectquotation.ProjectQuotation;
import cn.iocoder.yudao.module.jl.mapper.commontask.CommonTaskMapper;
import cn.iocoder.yudao.module.jl.mapper.projectquotation.ProjectQuotationMapper;
import cn.iocoder.yudao.module.jl.service.commontask.CommonTaskService;
import cn.iocoder.yudao.module.jl.service.project.ProjectCategoryService;
import cn.iocoder.yudao.module.jl.service.project.ProjectService;
import cn.iocoder.yudao.module.jl.service.projectquotation.ProjectQuotationService;
import cn.iocoder.yudao.module.system.controller.admin.util.vo.UtilStoreGetReqVO;
import cn.iocoder.yudao.module.system.service.util.UtilServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.COMMON_TASK_NOT_EXISTS;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.PROJECT_QUOTATION_NOT_EXISTS;

@Tag(name = "管理后台 - 报价开放接口")
@RestController
@RequestMapping("/common-task")
@Validated
public class JLOpenCommonTaskController {
    @Resource
    private UtilServiceImpl utilService;

    @Resource
    private CommonTaskService commonTaskService;

    @Resource
    private CommonTaskMapper commonTaskMapper;

    @PostMapping("/get")
    @Operation(summary = "通过 ID 获得")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<CommonTaskRespVO> getProjectQuotation(@RequestParam("id") Long id, @RequestBody @Valid UtilStoreGetReqVO pageVO) {
        utilService.validStoreWithException(pageVO);
        Optional<CommonTask> commonTask = commonTaskService.getCommonTask(id);
        return success(commonTask.map(commonTaskMapper::toDto).orElseThrow(() -> exception(COMMON_TASK_NOT_EXISTS)));
    }

    @PostMapping("/page")
    @Operation(summary = "通过 ID 获得项目报价")
    public CommonResult<PageResult<CommonTaskRespVO>> geProjectCategoryPage(@RequestBody @Valid CommonTaskPageReqVO pageVO, @Valid CommonTaskPageOrder orderV0) {
        utilService.validStoreWithException(pageVO);
        PageResult<CommonTask> pageResult = commonTaskService.getCommonTaskPage(pageVO, orderV0);
        return success(commonTaskMapper.toPage(pageResult));
    }

}
