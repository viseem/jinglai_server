package cn.iocoder.yudao.module.jl.repository.worktodo;

import cn.iocoder.yudao.module.jl.entity.worktodo.WorkTodo;
import org.springframework.data.jpa.repository.*;

/**
* WorkTodoRepository
*
*/
public interface WorkTodoRepository extends JpaRepository<WorkTodo, Long>, JpaSpecificationExecutor<WorkTodo> {

}
