package cn.iocoder.yudao.module.system.service.util;

import cn.hutool.core.util.StrUtil;
import cn.iocoder.yudao.module.system.controller.admin.util.vo.UtilStoreGetReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.time.Duration;
import java.util.UUID;

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

    @Override
    public String generateIdAndSet(String jsonStr, String pwd, int expirationTime) {
        String id = generateUniqueId();

        String redisKey  = id + ":" + pwd;
        // 将信息存储到Redis中
        ValueOperations<String, String> valueOps = stringRedisTemplate.opsForValue();
        valueOps.set(redisKey, jsonStr);
        // 设置过期时间
        stringRedisTemplate.expire(redisKey, Duration.ofDays(expirationTime));
        return id;
    }

    private String generateUniqueId() {
        // 使用UUID生成唯一标识符
        String string = UUID.randomUUID().toString();
        // 去掉横线-
        return string.replaceAll("-", "");
    }


}
