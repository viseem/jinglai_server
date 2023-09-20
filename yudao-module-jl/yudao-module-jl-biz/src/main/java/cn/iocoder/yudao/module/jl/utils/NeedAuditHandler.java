package cn.iocoder.yudao.module.jl.utils;

import cn.iocoder.yudao.module.jl.entity.auditconfig.AuditConfig;
import cn.iocoder.yudao.module.jl.repository.auditconfig.AuditConfigRepository;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
@Repository
public class NeedAuditHandler {

    @Resource
    private AuditConfigRepository auditConfigRepository;

    public Boolean needAudit(HttpServletRequest request, String status){
        String route = request.getRequestURI();
        if (status!=null){
            AuditConfig byRouteAndStatus = auditConfigRepository.findByRouteAndStatusAndNeedAudit(route, status);
            return byRouteAndStatus!=null;
        }else{
            AuditConfig byRouteAndNeedAudit = auditConfigRepository.findByRouteAndNeedAudit(route);
            return byRouteAndNeedAudit!=null;
        }
    }

}
