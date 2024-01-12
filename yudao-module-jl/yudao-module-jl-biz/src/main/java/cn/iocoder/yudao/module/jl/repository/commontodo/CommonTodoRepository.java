package cn.iocoder.yudao.module.jl.repository.commontodo;

import cn.iocoder.yudao.module.jl.entity.commontodo.CommonTodo;
import org.springframework.data.jpa.repository.*;

/**
* CommonTodoRepository
*
*/
public interface CommonTodoRepository extends JpaRepository<CommonTodo, Long>, JpaSpecificationExecutor<CommonTodo> {

}
