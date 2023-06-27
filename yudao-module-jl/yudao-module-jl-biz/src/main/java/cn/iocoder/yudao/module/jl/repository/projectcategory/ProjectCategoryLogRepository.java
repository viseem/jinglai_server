package cn.iocoder.yudao.module.jl.repository.projectcategory;

import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryLog;
import org.springframework.data.jpa.repository.*;

/**
* ProjectCategoryLogRepository
*
*/
public interface ProjectCategoryLogRepository extends JpaRepository<ProjectCategoryLog, Long>, JpaSpecificationExecutor<ProjectCategoryLog> {

}
