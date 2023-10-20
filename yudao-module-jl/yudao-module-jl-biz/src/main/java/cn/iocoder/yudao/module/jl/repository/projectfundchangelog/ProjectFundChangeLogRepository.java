package cn.iocoder.yudao.module.jl.repository.projectfundchangelog;

import cn.iocoder.yudao.module.jl.entity.projectfundchangelog.ProjectFundChangeLog;
import org.springframework.data.jpa.repository.*;

/**
* ProjectFundChangeLogRepository
*
*/
public interface ProjectFundChangeLogRepository extends JpaRepository<ProjectFundChangeLog, Long>, JpaSpecificationExecutor<ProjectFundChangeLog> {

}
