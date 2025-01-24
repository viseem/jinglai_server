package cn.iocoder.yudao.module.jl.repository.projectsettlement;

import cn.iocoder.yudao.module.jl.entity.projectsettlement.ProjectSettlement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* ProjectSettlementRepository
*
*/
public interface ProjectSettlementRepository extends JpaRepository<ProjectSettlement, Long>, JpaSpecificationExecutor<ProjectSettlement> {
    @Query("select p from ProjectSettlement p where p.projectId = ?1")
    List<ProjectSettlement> findByProjectId(Long projectId);

}
