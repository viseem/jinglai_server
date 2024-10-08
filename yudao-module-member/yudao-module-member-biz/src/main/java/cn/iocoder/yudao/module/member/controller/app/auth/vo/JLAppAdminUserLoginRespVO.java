package cn.iocoder.yudao.module.member.controller.app.auth.vo;

import cn.iocoder.yudao.module.jl.controller.admin.user.vo.UserRespVO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class JLAppAdminUserLoginRespVO  {

    private String token;

    private UserRespVO user;
}
