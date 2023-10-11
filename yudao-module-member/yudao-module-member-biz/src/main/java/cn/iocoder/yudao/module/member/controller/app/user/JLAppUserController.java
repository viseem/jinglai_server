package cn.iocoder.yudao.module.member.controller.app.user;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.web.core.util.WebFrameworkUtils;
import cn.iocoder.yudao.module.jl.controller.admin.crm.vo.appcustomer.CustomerProjectPageReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.ProjectPageOrder;
import cn.iocoder.yudao.module.jl.entity.project.AppProject;
import cn.iocoder.yudao.module.jl.entity.project.ProjectCategoryOnly;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
import cn.iocoder.yudao.module.jl.enums.ProjectCategoryStatusEnums;
import cn.iocoder.yudao.module.jl.repository.project.AppProjectRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectRepository;
import cn.iocoder.yudao.module.jl.service.project.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.exception.enums.GlobalErrorCodeConstants.UNAUTHORIZED;
import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.PROJECT_NOT_EXISTS;

@Tag(name = "用户 APP - 用户个人中心")
@RestController
@RequestMapping("/member/user")
@Validated
@Slf4j
public class JLAppUserController {

    @Resource
    ProjectService projectService;

    @Resource
    AppProjectRepository appProjectRepository;

    @PostMapping("/project-page")
    @Operation(summary = "(分页)获得项目管理列表")
    public CommonResult<PageResult<ProjectSimple>> getProjectPage(@Valid @RequestBody CustomerProjectPageReqVO pageVO, @Valid @RequestBody ProjectPageOrder orderV0) {
        Long loginUserId = WebFrameworkUtils.getLoginUserId();
        if (loginUserId == null) {
            throw exception(UNAUTHORIZED);
        }
        pageVO.setCustomerId(loginUserId);

        PageResult<ProjectSimple> pageResult = projectService.getCustomerProjectPage(pageVO, orderV0);
        return success(pageResult);
    }


    @GetMapping("/project-detail")
    @Operation(summary = "(分页)获得项目管理列表")
    public CommonResult<AppProject> getProjectDetail(@RequestParam("id") Long id) {
        AppProject appProject = appProjectRepository.findById(id).orElseThrow(() -> exception(PROJECT_NOT_EXISTS));

        // 计算categoryList的完成个数,通过categoryList的status来判断，等于COMPLETE的个数
        int completeCount = 0;
        int allCount = appProject.getCategoryList().size();

        for (ProjectCategoryOnly category : appProject.getCategoryList()) {
            if (category.getStage().equals(ProjectCategoryStatusEnums.COMPLETE.getStatus())) {
                completeCount++;
            }
        }

        appProject.setCompleteCount(completeCount);
        appProject.setAllCount(appProject.getCategoryList().size());
        appProject.setCompletePercent((completeCount*100/allCount));

        return success(appProject);
    }


}

