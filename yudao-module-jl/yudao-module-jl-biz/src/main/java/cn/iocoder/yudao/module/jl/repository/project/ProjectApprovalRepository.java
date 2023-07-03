package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectApproval;
import org.springframework.data.jpa.repository.*;

/**
* ProjectApprovalRepository
*
*/
public interface ProjectApprovalRepository extends JpaRepository<ProjectApproval, Long>, JpaSpecificationExecutor<ProjectApproval> {

}
