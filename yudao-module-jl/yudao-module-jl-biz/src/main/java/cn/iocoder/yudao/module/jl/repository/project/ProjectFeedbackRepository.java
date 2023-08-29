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
    @Query("select count(p) from ProjectFeedback p where p.status <> ?1 or p.status is null")
    Integer countByStatusNot(String status);
    @Transactional
    @Modifying
    @Query("update ProjectFeedback p set p.result = ?1, p.resultUserId = ?2, p.resultTime = ?3, p.status = ?4 where p.id = ?5")
    void replyFeedback(String result, Long resultUserId, LocalDateTime resultTime, String status,Long id);

}
