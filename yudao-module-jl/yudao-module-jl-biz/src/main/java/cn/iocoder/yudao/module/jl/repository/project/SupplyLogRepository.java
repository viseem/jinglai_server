package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.SupplyLog;
import org.springframework.data.jpa.repository.*;

/**
* SupplyLogRepository
*
*/
public interface SupplyLogRepository extends JpaRepository<SupplyLog, Long>, JpaSpecificationExecutor<SupplyLog> {

}
