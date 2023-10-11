package cn.iocoder.yudao.module.member.controller.app.auth.vo;

import lombok.Data;

@Data
public class JLAppLoginRespVO {

    private String name;
    private Long userId;
    private String phone;
    private String token;
}
