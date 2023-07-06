package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectReimburse;
import org.springframework.data.jpa.repository.*;

/**
* ProjectReimburseRepository
*
*/
public interface ProjectReimburseRepository extends JpaRepository<ProjectReimburse, Long>, JpaSpecificationExecutor<ProjectReimburse> {

}
