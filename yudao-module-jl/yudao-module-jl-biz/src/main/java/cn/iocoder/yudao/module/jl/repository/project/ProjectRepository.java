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
    @Query("select count(p) from Project p where p.managerId = ?1 and (p.stage <> ?2 or p.stage is null)")
    Integer countByManagerIdAndStageNot(Long managerId, String stage);
    @Query("select count(p) from Project p where p.creator = ?1 and p.stage = ?2")
    Integer countByCreatorAndStage(Long creator, String stage);

    @Query("select count(p) from Project p where p.creator = ?1 and (p.stage <> ?2 or p.stage is null)")
    Integer countByCreatorAndStageNot(Long creator, String stage);

    @Query("select count(p) from Project p where p.stage = ?1")
    Integer countByStage(String stage);


    @Transactional
    @Modifying
    @Query("update Project p set p.currentScheduleId = ?2 where p.id = ?1")
    int updateCurrentScheduleIdById(Long projectId, Long scheduleId);


    Project findFirstByOrderByIdDesc();


}
