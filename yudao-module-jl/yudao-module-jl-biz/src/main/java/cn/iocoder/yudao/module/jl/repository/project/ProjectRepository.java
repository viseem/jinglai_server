package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.Project;
import cn.iocoder.yudao.module.jl.mapper.project.ProjectMapper;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
* ProjectRepository
*
*/
public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {
    @Query("select count(p) from Project p where p.managerId = ?1 and (p.stage <> ?2 or p.stage is null) and p.code is not null")
    Integer countByManagerIdAndStageNot(Long managerId, String stage);
    @Query("select count(p) from Project p where p.creator = ?1 and p.stage = ?2")
    Integer countByCreatorAndStage(Long creator, String stage);

    @Query("select count(p) from Project p where p.creator = ?1 and (p.stage <> ?2 or p.stage is null)")
    Integer countByCreatorAndStageNot(Long creator, String stage);

    @Query("select count(p) from Project p where p.salesId = ?1 and (p.stage <> ?2 or p.stage is null) and p.code is not null")
    Integer countBySalesIdAndStageNot(Long creator, String stage);

    @Query("select count(p) from Project p where p.stage = ?1")
    Integer countByStage(String stage);
    @Query("select count(p) from Project p where p.stage = ?1 and p.managerId = ?2")
    Integer countByStageAndManagerId(String stage, Long managerId);

    @Query("select count(p) from Project p where p.stage = ?1 and p.salesId = ?2")
    Integer countByStageAndSalesId(String stage, Long salesId);

    @Transactional
    @Modifying
    @Query("update Project p set p.currentScheduleId = ?2 where p.id = ?1")
    int updateCurrentScheduleIdById(Long projectId, Long scheduleId);


    Project findFirstByOrderByIdDesc();


}
