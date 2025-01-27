package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectCategory;
import cn.iocoder.yudao.module.jl.entity.project.ProjectCategorySimple;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* ProjectCategoryRepository
*
*/
public interface ProjectCategorySimpleRepository extends JpaRepository<ProjectCategorySimple, Long>, JpaSpecificationExecutor<ProjectCategorySimple> {

    List<ProjectCategorySimple> findAll(Specification<ProjectCategorySimple> specification);

    @Query("select count(1) from ProjectCategory p where p.projectId = ?1 and p.stage = ?2 and p.type = 'schedule'")
    long countByProjectIdAndStageAndType(Long projectId, String stage);
    @Query("select count(1) from ProjectCategory p where p.projectId = ?1 and p.type = 'schedule'")
    long countByProjectIdAndType(Long projectId);
    @Query("select count(1) from ProjectCategory p where p.stage = ?1")
    long countByStage(String stage);
    @Transactional
    @Modifying
    @Query("update ProjectCategory p set p.stage = ?1 where p.id = ?2")
    int updateStageById(String stage, Long id);
    @Query("select p from ProjectCategory p where p.projectId = ?1 and p.scheduleId = ?2 and p.type = ?3")
    ProjectCategory findByProjectIdAndScheduleIdAndType(Long projectId, Long scheduleId, String type);
    @Query("select p from ProjectCategory p where p.projectId = ?1 and p.type = ?2")
    ProjectCategory findByProjectIdAndType(Long projectId, String type);
    @Transactional
    @Modifying
    @Query("update ProjectCategory p set p.deleted = ?1 where p.id = ?2")
    int updateDeletedById(Boolean deleted, Long id);
    @Transactional
    @Modifying
    @Query("update ProjectCategory p set p.content = ?1 where p.id = ?2")
    int updateContentById(String content, Long id);
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
