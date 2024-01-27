package cn.iocoder.yudao.module.jl.controller.admin.subjectgroup;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.jl.controller.admin.subjectgroup.vo.*;
import cn.iocoder.yudao.module.jl.controller.admin.subjectgroup.vo.subgroupworkstation.SubjectGroupFollowupStatsReqVO;
import cn.iocoder.yudao.module.jl.entity.subjectgroup.SubjectGroup;
import cn.iocoder.yudao.module.jl.mapper.subjectgroup.SubjectGroupMapper;
import cn.iocoder.yudao.module.jl.service.subjectgroup.SubjectGroupWorkstationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.Optional;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.SUBJECT_GROUP_NOT_EXISTS;

@Tag(name = "管理后台 - 专题小组")
@RestController
@RequestMapping("/jl/subject-group-workstation")
@Validated
public class SubjectGroupStatsController {

    @Resource
    private SubjectGroupWorkstationService subjectGroupWorkstationService;

    @Resource
    private SubjectGroupMapper subjectGroupMapper;

    @GetMapping("/count-followup")
    @Operation(summary = "获取跟进的统计数据")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:subject-group:query')")
    public CommonResult<SubjectGroupRespVO> getSubjectGroupFollowupStats(@Valid @RequestBody SubjectGroupFollowupStatsReqVO reqVO) {
        return success(null);
    }
}
