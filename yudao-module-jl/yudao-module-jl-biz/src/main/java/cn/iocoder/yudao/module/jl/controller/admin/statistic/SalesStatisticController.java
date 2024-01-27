package cn.iocoder.yudao.module.jl.controller.admin.statistic;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.jl.controller.admin.subjectgroup.vo.*;
import cn.iocoder.yudao.module.jl.controller.admin.subjectgroup.vo.subgroupworkstation.SubjectGroupFollowupStatsReqVO;
import cn.iocoder.yudao.module.jl.mapper.subjectgroup.SubjectGroupMapper;
import cn.iocoder.yudao.module.jl.service.subjectgroup.SubjectGroupStatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "销售端数据统计")
@RestController
@RequestMapping("/statistic/sales")
@Validated
public class SalesStatisticController {

    @Resource
    private SubjectGroupStatsService subjectGroupStatsService;

    @Resource
    private SubjectGroupMapper subjectGroupMapper;

    @GetMapping("/followup")
    @Operation(summary = "获取跟进的统计数据")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    @PreAuthorize("@ss.hasPermission('jl:subject-group:query')")
    public CommonResult<SubjectGroupRespVO> getSubjectGroupFollowupStats(@Valid @RequestBody SubjectGroupFollowupStatsReqVO reqVO) {
        return success(null);
    }
}
