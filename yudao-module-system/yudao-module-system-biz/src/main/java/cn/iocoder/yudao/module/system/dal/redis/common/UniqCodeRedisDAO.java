package cn.iocoder.yudao.module.system.dal.redis.common;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.Objects;

import static cn.iocoder.yudao.module.system.dal.redis.RedisKeyConstants.PREFIX_PROJECT_CODE;

@Repository
public class UniqCodeRedisDAO {

    @Resource
    private StringRedisTemplate stringRedisTemplate;


    public Long generateUniqUid(String key) {
        return stringRedisTemplate.opsForValue().increment(key);
    }

    public void setInitUniqUid(String key,Long id){
        stringRedisTemplate.opsForValue().set(key, String.valueOf(id));
    }

    public Long getUniqUidByKey(String key){
        String s = stringRedisTemplate.opsForValue().get(key);
        if (s==null){
            s = "0";
        }
        return Long.valueOf(s);
    }

    public void setUniqCodePrefix(String key,String prefix){
        stringRedisTemplate.opsForValue().set(key,prefix);
    }

    public String getUniqCodePrefix(String key,String defaultPrefix){

        String s = stringRedisTemplate.opsForValue().get(key);
        if (s==null || s.length()==0){
            stringRedisTemplate.opsForValue().set(key,defaultPrefix);
            return defaultPrefix;
        }
        return s;
    }
}
