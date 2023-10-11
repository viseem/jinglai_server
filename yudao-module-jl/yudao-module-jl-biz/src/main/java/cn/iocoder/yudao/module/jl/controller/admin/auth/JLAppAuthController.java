package cn.iocoder.yudao.module.jl.controller.admin.auth;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.operatelog.core.annotations.OperateLog;
import cn.iocoder.yudao.module.jl.controller.admin.auth.vo.JLAppLoginByPhoneReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.auth.vo.JLAppLoginRespVO;
import cn.iocoder.yudao.module.jl.service.auth.JLAuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "登录授权")
@RestController
@RequestMapping("/jl/app/auth")
@Validated
public class JLAppAuthController {

    @Resource
    private JLAuthService jlAuthService;

    @PostMapping("/login-by-phonecode")
    @PermitAll
    @Operation(summary = "小程序登录")
    @OperateLog(enable = false) // 避免 Post 请求被记录操作日志
    public CommonResult<JLAppLoginRespVO> loginByPhone(@Valid @RequestBody JLAppLoginByPhoneReqVO reqVO) {
        return success(jlAuthService.loginByPhoneCode(reqVO));
    }
}
