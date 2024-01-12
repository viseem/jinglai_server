package cn.iocoder.yudao.module.jl.repository.commontodolog;

import cn.iocoder.yudao.module.jl.entity.commontodolog.CommonTodoLog;
import org.springframework.data.jpa.repository.*;

/**
* CommonTodoLogRepository
*
*/
public interface CommonTodoLogRepository extends JpaRepository<CommonTodoLog, Long>, JpaSpecificationExecutor<CommonTodoLog> {

}
