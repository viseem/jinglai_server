package cn.iocoder.yudao.module.jl.repository.customerchangelog;

import cn.iocoder.yudao.module.jl.entity.customerchangelog.CustomerChangeLog;
import org.springframework.data.jpa.repository.*;

/**
* CustomerChangeLogRepository
*
*/
public interface CustomerChangeLogRepository extends JpaRepository<CustomerChangeLog, Long>, JpaSpecificationExecutor<CustomerChangeLog> {

}
