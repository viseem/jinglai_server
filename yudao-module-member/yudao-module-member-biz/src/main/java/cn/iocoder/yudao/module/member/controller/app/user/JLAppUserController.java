package cn.iocoder.yudao.module.member.controller.app.user;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.web.core.util.WebFrameworkUtils;
import cn.iocoder.yudao.module.jl.controller.admin.crm.vo.AppCustomerUpdateReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.crm.vo.appcustomer.CustomerProjectPageReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.ProjectConstractPageOrder;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.ProjectConstractPageReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.ProjectPageOrder;
import cn.iocoder.yudao.module.jl.entity.crm.CustomerOnly;
import cn.iocoder.yudao.module.jl.entity.project.*;
import cn.iocoder.yudao.module.jl.enums.ProjectCategoryStatusEnums;
import cn.iocoder.yudao.module.jl.repository.project.AppProjectRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectCategoryRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectCategorySimpleRepository;
import cn.iocoder.yudao.module.jl.service.crm.CustomerService;
import cn.iocoder.yudao.module.jl.service.project.ProjectConstractService;
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
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.PROJECT_CATEGORY_NOT_EXISTS;
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

    @Resource
    ProjectCategoryRepository projectCategoryRepository;


    @Resource
    ProjectConstractService projectConstractService;


    @Resource
    CustomerService customerService;

    private Long validLoginUser(){
        Long loginUserId = WebFrameworkUtils.getLoginUserId();
        if (loginUserId == null) {
            throw exception(UNAUTHORIZED);
        }
        return loginUserId;
    }

    @PostMapping("/project-page")
    @Operation(summary = "客户 项目列表")
    public CommonResult<PageResult<ProjectSimple>> getProjectPage(@Valid @RequestBody CustomerProjectPageReqVO pageVO, @Valid @RequestBody ProjectPageOrder orderV0) {
        Long loginUserId = validLoginUser();
        pageVO.setCustomerId(loginUserId);

        PageResult<ProjectSimple> pageResult = projectService.getCustomerProjectPage(pageVO, orderV0);
        return success(pageResult);
    }


    @GetMapping("/project-detail")
    @Operation(summary = "APP 项目详情")
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
        if(allCount!=0){
            appProject.setCompletePercent((completeCount*100/allCount));
        }

        return success(appProject);
    }


    @PostMapping("/contract-page")
    @Operation(summary = "客户 合同列表")
    public CommonResult<PageResult<ProjectConstract>> getContractPage(@Valid @RequestBody ProjectConstractPageReqVO pageVO, @Valid @RequestBody ProjectConstractPageOrder orderV0) {
        PageResult<ProjectConstract> projectConstractPage = projectConstractService.getProjectConstractPage(pageVO, orderV0);
        return success(projectConstractPage);
    }

    @PostMapping("/update")
    //实际上更新的是customer表的信息
    @Operation(summary = "更新信息")
    public CommonResult<CustomerOnly> updateCustomer(@Valid @RequestBody AppCustomerUpdateReqVO updateReqVO) {
        Long loginUserId = validLoginUser();
        updateReqVO.setId(loginUserId);
        CustomerOnly customerOnly = customerService.updateAppCustomer(updateReqVO);
        return success(customerOnly);
    }


    @GetMapping("/category-detail")
    @Operation(summary = "APP 实验详情")
    public CommonResult<ProjectCategory> getProjectCategoryDetail(@RequestParam("id") Long id) {
        ProjectCategory projectCategory = projectCategoryRepository.findById(id).orElseThrow(() -> exception(PROJECT_CATEGORY_NOT_EXISTS));
        return success(projectCategory);
    }

}

