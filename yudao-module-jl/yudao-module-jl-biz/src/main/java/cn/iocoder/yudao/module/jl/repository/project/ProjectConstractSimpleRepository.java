package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectConstract;
import cn.iocoder.yudao.module.jl.entity.project.ProjectConstractOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* ProjectConstractRepository
*
*/
public interface ProjectConstractSimpleRepository extends JpaRepository<ProjectConstractOnly, Long>, JpaSpecificationExecutor<ProjectConstractOnly> {
    @Transactional
    @Modifying
    @Query("update ProjectConstractOnly p set p.projectManagerId = ?1 where p.projectId = ?2")
    int updateProjectManagerIdByProjectId(Long projectManagerId, Long projectId);
    @Transactional
    @Modifying
    @Query("update ProjectConstractOnly p set p.projectId = ?1 where p.id = ?2")
    int updateProjectIdById(Long projectId, Long id);
    @Transactional
    @Modifying
    @Query("update ProjectConstractOnly p set p.projectTagIds = ?1 where p.projectId = ?2")
    int updateProjectTagIdsByProjectId(String projectTagIds, Long projectId);
    @Transactional
    @Modifying
    @Query("update ProjectConstractOnly p set p.projectStage = ?1 where p.projectId = ?2")
    int updateProjectStageByProjectId(String projectStage, Long projectId);
    @Query("select p from ProjectConstract p where p.customerId = ?1 and p.status = ?2")
    List<ProjectConstract> findByCustomerIdAndStatus(Long customerId, String status);

    ProjectConstract findFirstByOrderByIdDesc();
}
