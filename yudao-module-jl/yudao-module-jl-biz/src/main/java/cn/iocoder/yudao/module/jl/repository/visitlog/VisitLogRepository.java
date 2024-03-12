package cn.iocoder.yudao.module.jl.repository.visitlog;

import cn.iocoder.yudao.module.jl.entity.visitlog.VisitLog;
import org.springframework.data.jpa.repository.*;

/**
* VisitLogRepository
*
*/
public interface VisitLogRepository extends JpaRepository<VisitLog, Long>, JpaSpecificationExecutor<VisitLog> {

}
