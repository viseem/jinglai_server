package cn.iocoder.yudao.module.jl.repository.taskproduct;

import cn.iocoder.yudao.module.jl.entity.taskproduct.TaskProduct;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
* TaskProductRepository
*
*/
public interface TaskProductRepository extends JpaRepository<TaskProduct, Long>, JpaSpecificationExecutor<TaskProduct> {
    @Query("select t from TaskProduct t where t.taskId = ?1")
    List<TaskProduct> findByTaskId(Long taskId);

}
