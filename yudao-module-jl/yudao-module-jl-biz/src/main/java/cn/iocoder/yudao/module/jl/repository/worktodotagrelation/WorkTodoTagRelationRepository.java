package cn.iocoder.yudao.module.jl.repository.worktodotagrelation;

import cn.iocoder.yudao.module.jl.entity.worktodotagrelation.WorkTodoTagRelation;
import org.springframework.data.jpa.repository.*;

/**
* WorkTodoTagRelationRepository
*
*/
public interface WorkTodoTagRelationRepository extends JpaRepository<WorkTodoTagRelation, Long>, JpaSpecificationExecutor<WorkTodoTagRelation> {

}
