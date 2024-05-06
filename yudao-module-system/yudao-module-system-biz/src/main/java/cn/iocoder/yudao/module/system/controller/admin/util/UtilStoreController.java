package cn.iocoder.yudao.module.system.controller.admin.util;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.system.controller.admin.util.vo.UtilStoreReqVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.Duration;
import java.util.UUID;

@Tag(name = "管理后台 - 存储服务")
@RestController
@Validated
public class UtilStoreController {
    @PostMapping("/store")
    @Operation(summary = "存储")
    public CommonResult<String> store(@RequestBody @Valid UtilStoreReqVO reqVO) {
        String s = generateIdAndSet(reqVO.getJsonStr(), reqVO.getPwd(), reqVO.getExpireTime());
        return CommonResult.success(s);
    }

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public String generateIdAndSet(String jsonStr, String pwd, int expirationTime) {
        String id = generateUniqueId(jsonStr);

        String redisKey  = id + ":" + pwd;
        // 将信息存储到Redis中
        ValueOperations<String, String> valueOps = stringRedisTemplate.opsForValue();
        valueOps.set(redisKey, jsonStr);
        // 设置过期时间
        stringRedisTemplate.expire(redisKey, Duration.ofDays(expirationTime));
        return id;
    }

    private String generateUniqueId(String jsonStr) {
        // 使用UUID生成唯一标识符
        String string = UUID.randomUUID().toString();
        // 去掉横线-
        return string.replaceAll("-", "");
    }
}
