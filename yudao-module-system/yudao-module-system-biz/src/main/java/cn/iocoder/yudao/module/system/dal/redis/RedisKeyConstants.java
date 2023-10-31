package cn.iocoder.yudao.module.system.dal.redis;

import cn.iocoder.yudao.framework.redis.core.RedisKeyDefine;
import cn.iocoder.yudao.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;

import java.time.Duration;

import static cn.iocoder.yudao.framework.redis.core.RedisKeyDefine.KeyTypeEnum.STRING;

/**
 * System Redis Key 枚举类
 *
 * @author 芋道源码
 */
public interface RedisKeyConstants {

    RedisKeyDefine CAPTCHA_CODE = new RedisKeyDefine("验证码的缓存",
            "captcha_code:%s", // 参数为 uuid
            STRING, String.class, RedisKeyDefine.TimeoutTypeEnum.DYNAMIC);

    RedisKeyDefine OAUTH2_ACCESS_TOKEN = new RedisKeyDefine("访问令牌的缓存",
            "oauth2_access_token:%s", // 参数为访问令牌 token
            STRING, OAuth2AccessTokenDO.class, RedisKeyDefine.TimeoutTypeEnum.DYNAMIC);

    RedisKeyDefine SOCIAL_AUTH_STATE = new RedisKeyDefine("社交登陆的 state", // 注意，它是被 JustAuth 的 justauth.type.prefix 使用到
            "social_auth_state:%s", // 参数为 state
            STRING, String.class, Duration.ofHours(24)); // 值为 state

    public String PROJECT_CODE_DEFAULT_PREFIX = "P";

    RedisKeyDefine AUTO_INCREMENT_KEY_PROJECT_CODE = new RedisKeyDefine("项目自增数字", // 注意，它是被 JustAuth 的 justauth.type.prefix 使用到
            "auto_increment_key:project_code", // 参数为 state
            STRING, String.class, RedisKeyDefine.TimeoutTypeEnum.FOREVER); // 值为 state

    RedisKeyDefine PREFIX_PROJECT_CODE = new RedisKeyDefine("项目流水号的前缀", // 注意，它是被 JustAuth 的 justauth.type.prefix 使用到
            "prefix:project_code", // 参数为 state
            STRING, String.class, RedisKeyDefine.TimeoutTypeEnum.FOREVER); // 值为 state


    public String PROJECT_CONTRACT_CODE_DEFAULT_PREFIX = "C";
    RedisKeyDefine AUTO_INCREMENT_KEY_PROJECT_CONTRACT_CODE = new RedisKeyDefine("合同自增数字", // 注意，它是被 JustAuth 的 justauth.type.prefix 使用到
            "auto_increment_key:project_contract_code", // 参数为 state
            STRING, String.class, RedisKeyDefine.TimeoutTypeEnum.FOREVER); // 值为 state

    RedisKeyDefine PREFIX_PROJECT_CONTRACT_CODE = new RedisKeyDefine("合同流水号的前缀", // 注意，它是被 JustAuth 的 justauth.type.prefix 使用到
            "prefix:project_contract_code", // 参数为 state
            STRING, String.class, RedisKeyDefine.TimeoutTypeEnum.FOREVER); // 值为 state


    public String ANIMAL_FEED_ORDER_DEFAULT_PREFIX = "AFOrd";
    RedisKeyDefine AUTO_INCREMENT_KEY_ANIMAL_FEED_ORDER = new RedisKeyDefine("动物饲养单自增数字", // 注意，它是被 JustAuth 的 justauth.type.prefix 使用到
            "auto_increment_key:animal_feed_order", // 参数为 state
            STRING, String.class, RedisKeyDefine.TimeoutTypeEnum.FOREVER); // 值为 state

    RedisKeyDefine PREFIX_ANIMAL_FEED_ORDER = new RedisKeyDefine("动物饲养单流水号的前缀", // 注意，它是被 JustAuth 的 justauth.type.prefix 使用到
            "prefix:animal_feed_order", // 参数为 state
            STRING, String.class, RedisKeyDefine.TimeoutTypeEnum.FOREVER); // 值为 state

    public String CUSTOMER_RECEIPT_DEFAULT_PREFIX = "INV";
    RedisKeyDefine AUTO_INCREMENT_KEY_CUSTOMER_RECEIPT = new RedisKeyDefine("客户发票申请编号自增数字", // 注意，它是被 JustAuth 的 justauth.type.prefix 使用到
            "auto_increment_key:customer_receipt", // 参数为 state
            STRING, String.class, RedisKeyDefine.TimeoutTypeEnum.FOREVER); // 值为 state

    RedisKeyDefine PREFIX_CUSTOMER_RECEIPT = new RedisKeyDefine("客户发票申请编号的前缀", // 注意，它是被 JustAuth 的 justauth.type.prefix 使用到
            "prefix:customer_receipt", // 参数为 state
            STRING, String.class, RedisKeyDefine.TimeoutTypeEnum.FOREVER); // 值为 state
}
