package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectSchedule;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* ProjectScheduleRepository
*
*/
public interface ProjectScheduleRepository extends JpaRepository<ProjectSchedule, Long>, JpaSpecificationExecutor<ProjectSchedule> {
    @Transactional
    @Modifying
    @Query("update ProjectSchedule p set p.currentPlanId = ?1 where p.id = ?2")
    int updateCurrentPlanIdById(Long currentPlanId, Long id);
    @Query("select count(p) from ProjectSchedule p where p.projectId = ?1")
    long countByProjectId(Long projectId);

}
