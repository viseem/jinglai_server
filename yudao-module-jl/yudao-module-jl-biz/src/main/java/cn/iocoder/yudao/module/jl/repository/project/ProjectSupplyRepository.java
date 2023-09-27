package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectSupply;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
* ProjectSupplyRepository
*
*/
public interface ProjectSupplyRepository extends JpaRepository<ProjectSupply, Long>, JpaSpecificationExecutor<ProjectSupply> {
    @Transactional
    @Modifying
    @Query("update ProjectSupply p set p.deleted = ?1 where p.projectCategoryId = ?2")
    int updateDeletedByProjectCategoryId(Boolean deleted, Long projectCategoryId);
    @Query("select p from ProjectSupply p where p.scheduleId = ?1")
    List<ProjectSupply> findByScheduleId(Long scheduleId);

    @Query("select p from ProjectSupply p where p.projectId = ?1")
    List<ProjectSupply> findByProjectId(Long projectId);
    @Transactional
    @Modifying
    @Query("delete from ProjectSupply p where p.projectCategoryId = ?1")
    int deleteByProjectCategoryId(Long projectCategoryId);
    @Transactional
    @Modifying
    @Query("delete from ProjectSupply p where p.projectCategoryId in ?1")
    void deleteByProjectCategoryIdIn(Collection<Long> projectCategoryIds);

}
