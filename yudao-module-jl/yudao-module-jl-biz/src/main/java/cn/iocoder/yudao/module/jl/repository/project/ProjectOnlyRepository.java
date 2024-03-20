package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* ProjectRepository
*
*/
public interface ProjectOnlyRepository extends JpaRepository<ProjectOnly, Long>, JpaSpecificationExecutor<ProjectOnly> {
    @Query("select p from ProjectOnly p where p.salesId in ?1 and p.code is not null and p.managerId not in ?2")
    List<ProjectOnly> findBySalesIdInAndCodeNotNullAndManagerIdNotIn(List<Long> salesIds, List<Long> managerIds);
    @Query("select p from ProjectOnly p where p.managerId in ?1 and p.code is not null")
    List<ProjectOnly> findByInManagerIdAndCodeNotNull(Long[] managerIds);

}
