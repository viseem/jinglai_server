package cn.iocoder.yudao.module.jl.repository.commonlog;

import cn.iocoder.yudao.module.jl.entity.commonlog.CommonLog;
import org.springframework.data.jpa.repository.*;

/**
* CommonLogRepository
*
*/
public interface CommonLogRepository extends JpaRepository<CommonLog, Long>, JpaSpecificationExecutor<CommonLog> {

}
