package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectReimburse;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
* ProjectReimburseRepository
*
*/
public interface ProjectReimburseRepository extends JpaRepository<ProjectReimburse, Long>, JpaSpecificationExecutor<ProjectReimburse> {
    @Query("select p from ProjectReimburse p where p.scheduleId = ?1")
    List<ProjectReimburse> findByScheduleId(Long scheduleId);

}
