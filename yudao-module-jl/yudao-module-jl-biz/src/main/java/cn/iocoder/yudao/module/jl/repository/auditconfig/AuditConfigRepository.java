package cn.iocoder.yudao.module.jl.repository.auditconfig;

import cn.iocoder.yudao.module.jl.entity.auditconfig.AuditConfig;
import org.springframework.data.jpa.repository.*;

/**
* AuditConfigRepository
*
*/
public interface AuditConfigRepository extends JpaRepository<AuditConfig, Long>, JpaSpecificationExecutor<AuditConfig> {
    @Query("select a from AuditConfig a where a.route = ?1 and a.needAudit = true")
    AuditConfig findByRouteAndNeedAudit(String route);
    @Query("select a from AuditConfig a where a.route = ?1 and a.status = ?2 and a.needAudit = true")
    AuditConfig findByRouteAndStatusAndNeedAudit(String route, String status);

}
