package cn.iocoder.yudao.module.jl.repository.commonoperatelog;

import cn.iocoder.yudao.module.jl.entity.commonoperatelog.CommonOperateLog;
import org.springframework.data.jpa.repository.*;

/**
* CommonOperateLogRepository
*
*/
public interface CommonOperateLogRepository extends JpaRepository<CommonOperateLog, Long>, JpaSpecificationExecutor<CommonOperateLog> {

}
