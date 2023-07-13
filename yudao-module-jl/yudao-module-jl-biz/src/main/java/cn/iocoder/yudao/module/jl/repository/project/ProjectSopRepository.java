package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectSop;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
* ProjectSopRepository
*
*/
public interface ProjectSopRepository extends JpaRepository<ProjectSop, Long>, JpaSpecificationExecutor<ProjectSop> {
    @Transactional
    @Modifying
    @Query("delete from ProjectSop p where p.projectCategoryId = ?1")
    int deleteByProjectCategoryId(Long projectCategoryId);
    @Transactional
    @Modifying
    @Query("update ProjectSop p set p.status = ?1 where p.id in ?2")
    int updateStatusByIdIn(String status, Collection<Long> ids);
    @Transactional
    @Modifying
    @Query("delete from ProjectSop p where p.projectCategoryId in ?1")
    void deleteByProjectCategoryIdIn(Collection<Long> projectCategoryIds);

}
