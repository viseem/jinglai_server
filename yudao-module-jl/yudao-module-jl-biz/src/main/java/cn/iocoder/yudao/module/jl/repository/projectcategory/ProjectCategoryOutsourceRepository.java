package cn.iocoder.yudao.module.jl.repository.projectcategory;

import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryOutsource;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
* ProjectCategoryOutsourceRepository
*
*/
public interface ProjectCategoryOutsourceRepository extends JpaRepository<ProjectCategoryOutsource, Long>, JpaSpecificationExecutor<ProjectCategoryOutsource> {
    @Query("select p from ProjectCategoryOutsource p where p.scheduleId = ?1")
    List<ProjectCategoryOutsource> findByScheduleId(Long scheduleId);

    @Query("select p from ProjectCategoryOutsource p where p.projectId = ?1")
    List<ProjectCategoryOutsource> findByProjectId(Long projectId);
}
