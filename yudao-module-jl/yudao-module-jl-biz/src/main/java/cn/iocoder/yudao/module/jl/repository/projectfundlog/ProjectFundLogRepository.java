package cn.iocoder.yudao.module.jl.repository.projectfundlog;

import cn.iocoder.yudao.module.jl.entity.projectfundlog.ProjectFundLog;
import org.springframework.data.jpa.repository.*;

/**
* ProjectFundLogRepository
*
*/
public interface ProjectFundLogRepository extends JpaRepository<ProjectFundLog, Long>, JpaSpecificationExecutor<ProjectFundLog> {

}
