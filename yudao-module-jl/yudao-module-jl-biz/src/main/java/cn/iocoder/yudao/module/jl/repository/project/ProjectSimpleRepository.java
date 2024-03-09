package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectOnly;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

/**
* ProjectRepository
*
*/
public interface ProjectSimpleRepository extends JpaRepository<ProjectSimple, Long>, JpaSpecificationExecutor<ProjectSimple> {
    @Query(value = "SELECT * FROM jl_project_base WHERE creator IN ?1", nativeQuery = true)
    List<ProjectSimple> findByCreatorIn(Long[] creators);
    @Query("select count(p) from ProjectSimple p where p.endDate between ?1 and ?2 and p.creator in ?3 and p.stage <> ?4")
    Integer countByEndDateBetweenAndCreatorInAndStageNot(LocalDate endDateStart, LocalDate endDateEnd, Long[] creators, String stage);
    @Query("select count(p) from ProjectSimple p where p.endDate < ?1 and p.creator in ?2 and p.stage <> ?3")
    Integer countByEndDateLessThanAndCreatorInAndStageNot(LocalDate endDate,  Long[] creators, String stage);
    @Query("select count(p) from ProjectSimple p where p.creator in ?1 and p.stage = ?2")
    Integer countByCreatorInAndStage(Long[]creators, String stage);
    @Query("select count(p) from ProjectSimple p where p.endDate between ?1 and ?2 and p.creator in ?3")
    Integer countByEndDateBetweenAndCreatorIn(LocalDate endDateStart, LocalDate endDateEnd, Long[] creators);

    @Query("select count(p) from ProjectSimple p where p.endDate between ?1 and ?2 and p.creator in ?3 and p.code is not null")
    Integer countByEndDateBetweenAndManagerInAndCodeNotNull(LocalDate endDateStart, LocalDate endDateEnd, Long[] managers);

    @Query("select count(p) from ProjectSimple p where p.stage <> ?1 and p.creator in ?2")
    Integer countByStageNotAndCreatorIn(String stage, Long[] creators);

    @Query("select count(p) from ProjectSimple p where p.stage <> ?1 and p.managerId in ?2 and p.code is not null")
    Integer countByStageNotAndManagerInAndCodeNotNull(String stage, Long[] managers);
    @Query("select count(p) from ProjectSimple p where p.code is not null and p.stage = ?1 and p.customerId = ?2")
    Integer countByCodeNotNullAndStageAndCustomerId(String stage, Long customerId);
    @Query("select count(p) from ProjectSimple p where p.code is not null and p.customerId = ?1")
    Integer countByCodeNotNullAndCustomerId(Long customerId);
    @Query("select p from ProjectSimple p where p.code is not null and p.salesId = ?1")
    List<ProjectSimple> findByCodeNotNullAndSalesId(Long salesId);
    @Query("select p from ProjectSimple p where p.code is not null and p.customerId = ?1")
    List<ProjectSimple> findByCodeNotNullAndCustomerId(Long customerId);


}
