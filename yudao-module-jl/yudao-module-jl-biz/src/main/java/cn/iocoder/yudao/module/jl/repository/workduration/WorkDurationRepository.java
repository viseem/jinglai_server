package cn.iocoder.yudao.module.jl.repository.workduration;

import cn.iocoder.yudao.module.jl.entity.workduration.WorkDuration;
import org.springframework.data.jpa.repository.*;

/**
* WorkDurationRepository
*
*/
public interface WorkDurationRepository extends JpaRepository<WorkDuration, Long>, JpaSpecificationExecutor<WorkDuration> {

}
