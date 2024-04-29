package cn.iocoder.yudao.module.system.controller.shortUrl;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletResponse;

@Tag(name = "管理后台 - 短链服务")
@RestController
@Validated
public class RedirectController {
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @GetMapping("/{shortIdentifier}")
    @PermitAll
    public void redirectToOriginalUrl(@PathVariable("shortIdentifier") String shortIdentifier, HttpServletResponse resp) throws Exception {
        System.out.println(66666);
        // 从Redis中获取原始URL
        ValueOperations<String, String> valueOps = stringRedisTemplate.opsForValue();
        String originalUrl = valueOps.get(shortIdentifier + ":url");

        if (originalUrl != null) {
            // 重定向到原始URL
            System.out.println("Redirecting to: " + originalUrl);
            // 这里可以选择301重定向或302临时重定向
            // 301：resp.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
            // 302：resp.setStatus(HttpServletResponse.SC_FOUND);
            // 这里使用302临时重定向
            // 另外需要注意的是，在实际应用中应该返回重定向状态码，这里简化为直接重定向
            resp.setStatus(HttpServletResponse.SC_FOUND);
            resp.setHeader("Location", originalUrl);
        } else {
            // 返回404 Not Found
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
