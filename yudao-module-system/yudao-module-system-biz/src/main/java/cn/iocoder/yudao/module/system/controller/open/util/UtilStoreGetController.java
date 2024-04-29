package cn.iocoder.yudao.module.system.controller.open.util;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.system.controller.admin.util.vo.UtilStoreGetReqVO;
import cn.iocoder.yudao.module.system.controller.admin.util.vo.UtilStoreReqVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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
public class UtilStoreGetController {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @GetMapping("/store/get")
    @Operation(summary = "获取存储")
    public CommonResult<String> get(@RequestBody @Valid UtilStoreGetReqVO reqVO) {
        String redisKey  = reqVO.getId() + ":" + reqVO.getPassword();
        ValueOperations<String, String> valueOps = stringRedisTemplate.opsForValue();
        String jsonStr = valueOps.get(redisKey);
        return CommonResult.success(jsonStr);
    }

}
