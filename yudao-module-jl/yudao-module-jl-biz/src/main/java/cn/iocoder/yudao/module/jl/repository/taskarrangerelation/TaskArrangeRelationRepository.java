package cn.iocoder.yudao.module.jl.repository.taskarrangerelation;

import cn.iocoder.yudao.module.jl.entity.taskarrangerelation.TaskArrangeRelation;
import org.springframework.data.jpa.repository.*;

/**
* TaskArrangeRelationRepository
*
*/
public interface TaskArrangeRelationRepository extends JpaRepository<TaskArrangeRelation, Long>, JpaSpecificationExecutor<TaskArrangeRelation> {

}
