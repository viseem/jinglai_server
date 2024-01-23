package cn.iocoder.yudao.module.jl.repository.commontodo;

import cn.iocoder.yudao.module.jl.entity.commontodo.CommonTodo;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
* CommonTodoRepository
*
*/
public interface CommonTodoRepository extends JpaRepository<CommonTodo, Long>, JpaSpecificationExecutor<CommonTodo> {
    @Query("select c from CommonTodo c where c.type = ?1")
    List<CommonTodo> findByType(String type);

}
