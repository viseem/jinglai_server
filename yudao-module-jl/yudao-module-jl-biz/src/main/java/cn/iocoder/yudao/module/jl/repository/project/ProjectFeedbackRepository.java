package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectFeedback;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
* ProjectFeedbackRepository
*
*/
public interface ProjectFeedbackRepository extends JpaRepository<ProjectFeedback, Long>, JpaSpecificationExecutor<ProjectFeedback> {
    @Transactional
    @Modifying
    @Query("update ProjectFeedback p set p.result = ?1, p.resultUserId = ?2, p.resultTime = ?3 where p.id = ?4")
    void replyFeedback(String result, Long resultUserId, LocalDateTime resultTime, Long id);

}
