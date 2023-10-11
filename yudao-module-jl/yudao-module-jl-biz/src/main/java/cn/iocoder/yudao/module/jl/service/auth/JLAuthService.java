package cn.iocoder.yudao.module.jl.service.auth;

import cn.iocoder.yudao.module.jl.controller.admin.auth.vo.JLAppLoginByPhoneReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.auth.vo.JLAppLoginRespVO;

import javax.validation.Valid;

/**
 * 公司资产（设备）预约 Service 接口
 *
 */
public interface JLAuthService {

    /**
     * 兼容晶莱客户 微信小程序登录
     *
     * @param reqVO 创建信息
     * @return 编号
     */
    JLAppLoginRespVO loginByPhoneCode(@Valid JLAppLoginByPhoneReqVO reqVO);


}
