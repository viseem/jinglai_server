package cn.iocoder.yudao.module.jl.controller.admin.auth.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JLAppLoginByPhoneReqVO {

    @Schema(description = "登录code")
    private String code;

    @Schema(description = "phone code", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotNull(message = "phone code不能为空")
    private String phoneCode;

    //测试phone，直接传phone获取token
    private String phone;

}
