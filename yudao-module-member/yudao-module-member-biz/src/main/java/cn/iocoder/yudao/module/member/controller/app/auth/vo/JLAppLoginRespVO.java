package cn.iocoder.yudao.module.member.controller.app.auth.vo;

import cn.iocoder.yudao.module.jl.controller.admin.crm.vo.CustomerBaseVO;
import cn.iocoder.yudao.module.jl.entity.crm.CustomerOnly;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class JLAppLoginRespVO extends CustomerBaseVO {

    private String token;
}
