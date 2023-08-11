package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectConstract;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
* ProjectConstractRepository
*
*/
public interface ProjectConstractRepository extends JpaRepository<ProjectConstract, Long>, JpaSpecificationExecutor<ProjectConstract> {
    @Query("select p from ProjectConstract p where p.customerId = ?1 and p.status = ?2")
    List<ProjectConstract> findByCustomerIdAndStatus(Long customerId, String status);

    ProjectConstract findFirstByOrderByIdDesc();
}
