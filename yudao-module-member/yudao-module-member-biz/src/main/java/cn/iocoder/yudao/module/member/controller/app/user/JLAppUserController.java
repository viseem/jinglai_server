package cn.iocoder.yudao.module.member.controller.app.user;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.web.core.util.WebFrameworkUtils;
import cn.iocoder.yudao.module.jl.controller.admin.crm.vo.appcustomer.CustomerProjectPageReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.ProjectPageOrder;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
import cn.iocoder.yudao.module.jl.service.project.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "用户 APP - 用户个人中心")
@RestController
@RequestMapping("/member/user")
@Validated
@Slf4j
public class JLAppUserController {

    @Resource
    ProjectService projectService;

    @PostMapping("/project-page")
    @Operation(summary = "(分页)获得项目管理列表")
    public CommonResult<PageResult<ProjectSimple>> getProjectPage(@Valid @RequestBody CustomerProjectPageReqVO pageVO, @Valid @RequestBody ProjectPageOrder orderV0) {
        Long loginUserId = WebFrameworkUtils.getLoginUserId();
        System.out.println("loginUserId---:"+loginUserId);
        pageVO.setCustomerId(loginUserId);

        System.out.println("pageVO:"+pageVO.getStageArr());

        PageResult<ProjectSimple> pageResult = projectService.getCustomerProjectPage(pageVO, orderV0);
        return success(pageResult);
    }

}

