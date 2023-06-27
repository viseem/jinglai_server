package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectSchedule;
import org.springframework.data.jpa.repository.*;

/**
* ProjectScheduleRepository
*
*/
public interface ProjectScheduleRepository extends JpaRepository<ProjectSchedule, Long>, JpaSpecificationExecutor<ProjectSchedule> {
    @Query("select count(p) from ProjectSchedule p where p.projectId = ?1")
    long countByProjectId(Long projectId);

}
