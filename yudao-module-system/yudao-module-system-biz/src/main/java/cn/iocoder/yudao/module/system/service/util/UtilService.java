package cn.iocoder.yudao.module.system.service.util;

import cn.iocoder.yudao.module.system.controller.admin.util.vo.UtilStoreGetReqVO;

import javax.validation.Valid;

/**
 * 后台用户 Service 接口
 *
 * @author 芋道源码
 */
public interface UtilService {

    /**
     * 创建用户
     *
     * @param reqVO 用户信息
     * @return 用户编号
     */
    String getStore(@Valid UtilStoreGetReqVO reqVO);

    Boolean validStoreWithException(@Valid UtilStoreGetReqVO reqVO);

}
