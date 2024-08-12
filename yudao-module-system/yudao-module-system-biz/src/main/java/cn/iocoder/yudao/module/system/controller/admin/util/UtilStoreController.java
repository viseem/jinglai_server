package cn.iocoder.yudao.module.system.controller.admin.util;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.system.controller.admin.util.vo.UtilStoreReqVO;
import cn.iocoder.yudao.module.system.service.util.UtilServiceImpl;
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

    @Resource
    private UtilServiceImpl utilService;

    @PostMapping("/store")
    @Operation(summary = "存储")
    public CommonResult<String> store(@RequestBody @Valid UtilStoreReqVO reqVO) {
        String s = utilService.generateIdAndSet(reqVO.getJsonStr(), reqVO.getPwd(), reqVO.getExpireTime());
        return CommonResult.success(s);
    }

}
