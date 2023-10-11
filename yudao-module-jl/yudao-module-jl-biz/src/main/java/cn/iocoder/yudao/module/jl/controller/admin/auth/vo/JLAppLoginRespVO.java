package cn.iocoder.yudao.module.jl.controller.admin.auth.vo;

import lombok.Data;

@Data
public class JLAppLoginRespVO {

    private String nickname;
    private Long userId;
    private String phone;
    private String token;
}
