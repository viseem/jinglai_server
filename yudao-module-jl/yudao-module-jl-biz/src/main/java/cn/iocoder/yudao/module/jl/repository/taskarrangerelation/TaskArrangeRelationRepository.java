package cn.iocoder.yudao.module.jl.repository.taskarrangerelation;

import cn.iocoder.yudao.module.jl.entity.taskarrangerelation.TaskArrangeRelation;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
* TaskArrangeRelationRepository
*
*/
public interface TaskArrangeRelationRepository extends JpaRepository<TaskArrangeRelation, Long>, JpaSpecificationExecutor<TaskArrangeRelation> {
    @Query("select t from TaskArrangeRelation t where t.taskId = ?1")
    TaskArrangeRelation findByTaskId(Long taskId);
    @Query("select t from TaskArrangeRelation t where t.quotationId = ?1")
    List<TaskArrangeRelation> findByQuotationId(Long quotationId);

}
