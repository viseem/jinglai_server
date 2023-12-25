package cn.iocoder.yudao.module.jl.controller.open.projectcategory;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.excel.core.util.ExcelUtils;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.controller.open.projectcategory.vo.LabExpSopUpdateReqVO;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSop;
import cn.iocoder.yudao.module.jl.mapper.project.ProjectSopMapper;
import cn.iocoder.yudao.module.jl.repository.project.ProjectSopRepository;
import cn.iocoder.yudao.module.jl.service.project.ProjectSopService;
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
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.PROJECT_SOP_NOT_EXISTS;

@Tag(name = "管理后台 - 项目中的实验名目的操作SOP")
@RestController
@RequestMapping("/lab/project-sop")
@Validated
public class LabProjectSopController {

    @Resource
    private ProjectSopService projectSopService;

    @Resource
    private ProjectSopRepository projectSopRepository;

    @PostMapping("/update-status")
    @Operation(summary = "更新项目中的实验名目的操作SOP")
    public CommonResult<Boolean> updateProjectSop(@Valid @RequestBody LabExpSopUpdateReqVO updateReqVO) {
        projectSopRepository.updateStatusById(updateReqVO.getStatus(),updateReqVO.getId());
        return success(true);
    }

}
