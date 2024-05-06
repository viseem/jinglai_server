package cn.iocoder.yudao.module.system.service.util;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.module.system.controller.admin.util.vo.UtilStoreGetReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertList;
import static cn.iocoder.yudao.framework.common.util.collection.CollectionUtils.convertSet;
import static cn.iocoder.yudao.module.system.enums.ErrorCodeConstants.*;

/**
 * 后台用户 Service 实现类
 *
 * @author 芋道源码
 */
@Service("utilServiceImpl")
@Slf4j
public class UtilServiceImpl implements UtilService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String getStore(@Valid UtilStoreGetReqVO reqVO) {
        String redisKey  = reqVO.getStoreId() + ":" + reqVO.getStorePwd();
        ValueOperations<String, String> valueOps = stringRedisTemplate.opsForValue();
        return valueOps.get(redisKey);
    }

    @Override
    public Boolean validStoreWithException(@Valid UtilStoreGetReqVO reqVO) {
        System.out.println(getStore(reqVO)+reqVO.getStoreId() + ":" + reqVO.getStorePwd() );
        if(StrUtil.isEmpty(getStore(reqVO))){
            throw exception(STORE_NOT_EXISTS);
        }
        return true;
    }


}
