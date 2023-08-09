package cn.iocoder.yudao.module.jl.repository.projectcategory;

import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryApproval;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* ProjectCategoryApprovalRepository
*
*/
public interface ProjectCategoryApprovalRepository extends JpaRepository<ProjectCategoryApproval, Long>, JpaSpecificationExecutor<ProjectCategoryApproval> {
    @Transactional
    @Modifying
    @Query("update ProjectCategoryApproval p set p.processInstanceId = ?1 where p.id = ?2")
    int updateProcessInstanceIdById(String processInstanceId, Long id);
    @Transactional
    @Modifying
    @Query("update ProjectCategoryApproval p set p.approvalId = ?1 where p.id = ?2")
    int updateApprovalIdById(Long approvalId, Long id);
    @Query("select p from ProjectCategoryApproval p where p.projectCategoryId = ?1")
    ProjectCategoryApproval findByProjectCategoryId(Long projectCategoryId);

}
