package cn.iocoder.yudao.module.system.controller.admin.util;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.system.controller.admin.socail.vo.SocialUserBindReqVO;
import cn.iocoder.yudao.module.system.controller.admin.util.vo.ShortUrlReqVO;
import cn.iocoder.yudao.module.system.controller.admin.util.vo.ShortUrlRespVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.time.Duration;
import java.util.UUID;

@Tag(name = "管理后台 - 短链服务")
@RestController
@Validated
public class ShortUrlController {
    @PostMapping("/system/short-url/generate")
    @Operation(summary = "社交绑定，使用 code 授权码")
    public CommonResult<ShortUrlRespVO> generate(@RequestBody @Valid ShortUrlReqVO reqVO) {
        ShortUrlRespVO shortUrlRespVO = new ShortUrlRespVO();
        String s = generateShortLink(reqVO.getOriginUrl(), reqVO.getPassword(), reqVO.getExpireTime());
        shortUrlRespVO.setShortUrl(s);
        return CommonResult.success(shortUrlRespVO);
    }

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public String generateShortLink(String originalUrl, String token, int expirationTime) {
        String shortIdentifier = generateUniqueShortIdentifier(originalUrl);

        // 将信息存储到Redis中
        ValueOperations<String, String> valueOps = stringRedisTemplate.opsForValue();
        valueOps.set(shortIdentifier + ":url", originalUrl);
        valueOps.set(shortIdentifier + ":token", token);

        // 设置过期时间
        stringRedisTemplate.expire(shortIdentifier + ":url", Duration.ofSeconds(expirationTime));
        stringRedisTemplate.expire(shortIdentifier + ":token", Duration.ofSeconds(expirationTime));

        return shortIdentifier;
    }

    private String generateUniqueShortIdentifier(String originalUrl) {
        // 使用UUID生成唯一标识符
        String shortIdentifier = UUID.randomUUID().toString().substring(0, 6);

        // 检查标识符是否已存在，如果存在，则重新生成
        while (Boolean.TRUE.equals(stringRedisTemplate.hasKey(shortIdentifier + ":url"))) {
            shortIdentifier = UUID.randomUUID().toString().substring(0, 6);
        }

        return shortIdentifier;
    }
}
