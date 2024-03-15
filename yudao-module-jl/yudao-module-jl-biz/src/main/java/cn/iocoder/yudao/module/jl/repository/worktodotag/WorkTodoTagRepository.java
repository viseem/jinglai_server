package cn.iocoder.yudao.module.jl.repository.worktodotag;

import cn.iocoder.yudao.module.jl.entity.worktodotag.WorkTodoTag;
import org.springframework.data.jpa.repository.*;

/**
* WorkTodoTagRepository
*
*/
public interface WorkTodoTagRepository extends JpaRepository<WorkTodoTag, Long>, JpaSpecificationExecutor<WorkTodoTag> {

}
