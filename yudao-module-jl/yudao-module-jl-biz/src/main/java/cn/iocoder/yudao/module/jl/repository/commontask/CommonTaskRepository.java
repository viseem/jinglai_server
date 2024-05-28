package cn.iocoder.yudao.module.jl.repository.commontask;

import cn.iocoder.yudao.module.jl.entity.commontask.CommonTask;
import org.springframework.data.jpa.repository.*;

/**
* CommonTaskRepository
*
*/
public interface CommonTaskRepository extends JpaRepository<CommonTask, Long>, JpaSpecificationExecutor<CommonTask> {

}
