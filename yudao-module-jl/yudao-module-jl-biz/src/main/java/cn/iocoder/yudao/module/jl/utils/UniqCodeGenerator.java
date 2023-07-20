package cn.iocoder.yudao.module.jl.utils;

import cn.iocoder.yudao.module.jl.repository.project.ProjectConstractRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectRepository;
import cn.iocoder.yudao.module.system.dal.redis.common.UniqCodeRedisDAO;
import liquibase.pro.packaged.M;
import liquibase.pro.packaged.T;
import org.springframework.context.annotation.Scope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Component
@Scope("prototype")
public class UniqCodeGenerator {

    @Resource
    private UniqCodeRedisDAO uniqCodeRedisDAO;

    private String uniqCodeKey;

    private String uniqCodePrefixKey;

    private String defaultPrefix;



    public void setInitUniqUid(Long id, String uniqCodeKey, String uniqCodePrefixKey, String defaultPrefix){

        this.uniqCodeKey = uniqCodeKey;
        this.uniqCodePrefixKey = uniqCodePrefixKey;
        this.defaultPrefix = defaultPrefix;

        uniqCodeRedisDAO.setUniqCodePrefix(uniqCodePrefixKey,defaultPrefix);

        Number aLong = uniqCodeRedisDAO.getUniqUidByKey(uniqCodeKey);
        if (aLong == null || !(aLong.longValue() > 0)) {
            uniqCodeRedisDAO.setInitUniqUid(uniqCodeKey, Long.valueOf(String.valueOf(id)));
        }
    }

    public Long generateUniqUid() {
        return uniqCodeRedisDAO.generateUniqUid(this.uniqCodeKey);
    }

    public Object getUniqCodePrefix() {
        return uniqCodeRedisDAO.getUniqCodePrefix(this.uniqCodePrefixKey,this.defaultPrefix);
    }
}
