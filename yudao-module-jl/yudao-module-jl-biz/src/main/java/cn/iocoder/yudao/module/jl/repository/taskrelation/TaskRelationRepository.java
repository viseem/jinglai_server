package cn.iocoder.yudao.module.jl.repository.taskrelation;

import cn.iocoder.yudao.module.jl.entity.taskrelation.TaskRelation;
import org.springframework.data.jpa.repository.*;

/**
* TaskRelationRepository
*
*/
public interface TaskRelationRepository extends JpaRepository<TaskRelation, Long>, JpaSpecificationExecutor<TaskRelation> {

}
