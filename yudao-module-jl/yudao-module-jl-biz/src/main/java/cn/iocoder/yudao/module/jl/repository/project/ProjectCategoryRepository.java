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
    @Query("select count(p) from ProjectCategory p where p.stage = ?1 and p.type = 'schedule' and FIND_IN_SET(?2,p.labIds)>0")
    Integer countByStageAndAndLabIdAndType(String stage,Long labId);
    @Query("select count(p) from ProjectCategory p where p.stage in ?1 and p.type = 'schedule' and FIND_IN_SET(?2,p.labIds)>0")
    Integer countInStageAndAndLabIdAndType(String[] stage,Long labId);
    @Transactional
    @Modifying
    @Query("update ProjectCategory p set p.type = ?1 where p.quotationId = ?2")
    int updateTypeByQuotationId(String type, Long quotationId);
    @Transactional
    @Modifying
    @Query("delete from ProjectCategory p where p.quotationId = ?1")
    int deleteByQuotationId(Long quotationId);
    @Query("select p from ProjectCategory p where p.quotationId = ?1")
    List<ProjectCategory> findByQuotationId(Long quotationId);
    @Transactional
    @Modifying
    @Query("update ProjectCategory p set p.quotationId = ?1 where p.quotationId = ?2")
    int updateQuotationIdByQuotationId(Long quotationId, Long quotationId1);
    @Transactional
    @Modifying
    @Query("update ProjectCategory p set p.quotationId = ?1 where p.id = ?2")
    int updateQuotationIdById(Long quotationId, Long id);
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

    @Query("select p from ProjectCategory p where p.projectId = ?1 and p.quotationId = ?2 and p.type = ?3")
    ProjectCategory findByProjectIdAndQuotationIdAndType(Long projectId, Long quotation, String type);

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
    @Query("select count(p) from ProjectCategory p where p.operatorId = ?1 and p.stage = ?2")
    Integer countByOperatorIdAndStage(Long operatorId, String stage);
    @Query("select count(p) from ProjectCategory p where p.operatorId = ?1 and p.stage in ?2")
    Integer countByOperatorIdAndInStage(Long operatorId, String[] stage);
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
