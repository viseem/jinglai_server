package cn.iocoder.yudao.module.jl.controller.admin.statistic;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.project.ProjectStatisticProjectResp;
import cn.iocoder.yudao.module.jl.controller.admin.statistic.vo.project.ProjectStatisticReqVO;
import cn.iocoder.yudao.module.jl.service.statistic.project.ProjectStatisticService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "销售端数据统计")
@RestController
@RequestMapping("/statistic/project")
@Validated
public class ProjectStatisticController {

    @Resource
    private ProjectStatisticService projectStatisticService;

    @GetMapping("/project")
    @Operation(summary = "获取项目的统计数据")
    @PreAuthorize("@ss.hasPermission('jl:subject-group:query')")
    public CommonResult<ProjectStatisticProjectResp> getSubjectGroupFollowupStats(@Valid @RequestBody ProjectStatisticReqVO reqVO) {
        ProjectStatisticProjectResp projectStatisticProjectResp = projectStatisticService.countProject(reqVO);
        return success(projectStatisticProjectResp);
    }

}