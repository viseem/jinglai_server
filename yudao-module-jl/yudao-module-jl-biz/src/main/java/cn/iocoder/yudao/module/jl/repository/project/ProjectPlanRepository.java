package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectPlan;
import org.springframework.data.jpa.repository.*;

/**
* ProjectPlanRepository
*
*/
public interface ProjectPlanRepository extends JpaRepository<ProjectPlan, Long>, JpaSpecificationExecutor<ProjectPlan> {
    @Query("select count(p) from ProjectPlan p where p.scheduleId = ?1")
    long countByScheduleId(Long scheduleId);

}
