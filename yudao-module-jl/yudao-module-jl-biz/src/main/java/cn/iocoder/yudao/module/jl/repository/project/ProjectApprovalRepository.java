package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectApproval;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* ProjectApprovalRepository
*
*/
public interface ProjectApprovalRepository extends JpaRepository<ProjectApproval, Long>, JpaSpecificationExecutor<ProjectApproval> {
    @Transactional
    @Modifying
    @Query("update ProjectApproval p set p.approvalStage = ?1 where p.id = ?2")
    int updateApprovalStageById(String approvalStage, Long id);
    @Transactional
    @Modifying
    @Query("update ProjectApproval p set p.processInstanceId = ?1 where p.id = ?2")
    int updateProcessInstanceIdById(String processInstanceId, Long id);

}
