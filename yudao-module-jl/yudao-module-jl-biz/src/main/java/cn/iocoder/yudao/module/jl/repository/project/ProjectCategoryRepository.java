package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* ProjectCategoryRepository
*
*/
public interface ProjectCategoryRepository extends JpaRepository<ProjectCategory, Long>, JpaSpecificationExecutor<ProjectCategory> {
    @Query("select count(p) from ProjectCategory p where p.operatorId = ?1 and (p.stage <> ?2 or p.stage is null)")
    Integer countByOperatorIdAndStageNot(Long operatorId, String stage);
    @Transactional
    @Modifying
    @Query("delete from ProjectCategory p where p.labId = ?1")
    int deleteByLabId(Long labId);
    @Transactional
    @Modifying
    @Query("update ProjectCategory p set p.hasFeedback = ?2 where p.id = ?1")
    void updateHasFeedbackById( Long id,Byte hasFeedback);
    @Transactional
    @Modifying
    @Query("delete from ProjectCategory p where p.scheduleId = ?1")
    int deleteByScheduleId(Long scheduleId);

    @Transactional
    @Query("select p from ProjectCategory p where p.scheduleId = ?1 order by p.id")
    List<ProjectCategory> findByScheduleIdOrderByIdAsc(Long scheduleId);

    @Modifying
    @Transactional
    @Query(value = "delete from ProjectCategory category where category.quoteId = ?1")
    void deleteByQuoteId(Long quoteId);

    @Modifying
    @Transactional
    @Query("SELECT e FROM ProjectCategory e WHERE e.quoteId = ?1")
    List<ProjectCategory> getByQuoteId(Long quoteId);
}
