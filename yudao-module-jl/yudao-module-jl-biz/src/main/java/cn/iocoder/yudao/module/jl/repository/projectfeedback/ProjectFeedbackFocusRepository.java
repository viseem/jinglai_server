package cn.iocoder.yudao.module.jl.repository.projectfeedback;

import cn.iocoder.yudao.module.jl.entity.projectfeedback.ProjectFeedbackFocus;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* ProjectFeedbackFocusRepository
*
*/
public interface ProjectFeedbackFocusRepository extends JpaRepository<ProjectFeedbackFocus, Long>, JpaSpecificationExecutor<ProjectFeedbackFocus> {
    @Transactional
    @Modifying
    @Query("delete from ProjectFeedbackFocus p where p.projectFeedbackId = ?1")
    int deleteByProjectFeedbackId(Long projectFeedbackId);

}
