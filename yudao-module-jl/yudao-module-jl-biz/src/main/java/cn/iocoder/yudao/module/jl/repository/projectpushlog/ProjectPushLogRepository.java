package cn.iocoder.yudao.module.jl.repository.projectpushlog;

import cn.iocoder.yudao.module.jl.entity.projectpushlog.ProjectPushLog;
import org.springframework.data.jpa.repository.*;

/**
* ProjectPushLogRepository
*
*/
public interface ProjectPushLogRepository extends JpaRepository<ProjectPushLog, Long>, JpaSpecificationExecutor<ProjectPushLog> {

}
