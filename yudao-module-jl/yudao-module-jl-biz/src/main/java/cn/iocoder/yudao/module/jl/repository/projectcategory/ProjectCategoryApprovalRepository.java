package cn.iocoder.yudao.module.jl.repository.projectcategory;

import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryApproval;
import org.springframework.data.jpa.repository.*;

/**
* ProjectCategoryApprovalRepository
*
*/
public interface ProjectCategoryApprovalRepository extends JpaRepository<ProjectCategoryApproval, Long>, JpaSpecificationExecutor<ProjectCategoryApproval> {
    @Query("select p from ProjectCategoryApproval p where p.projectCategoryId = ?1")
    ProjectCategoryApproval findByProjectCategoryId(Long projectCategoryId);

}
