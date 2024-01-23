package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* ProjectRepository
*
*/
public interface ProjectSimpleRepository extends JpaRepository<ProjectSimple, Long>, JpaSpecificationExecutor<ProjectSimple> {
    @Query("select count(p) from ProjectSimple p where p.code is not null and p.stage = ?1 and p.customerId = ?2")
    Integer countByCodeNotNullAndStageAndCustomerId(String stage, Long customerId);
    @Query("select count(p) from ProjectSimple p where p.code is not null and p.customerId = ?1")
    Integer countByCodeNotNullAndCustomerId(Long customerId);
    @Query("select p from ProjectSimple p where p.code is not null and p.salesId = ?1")
    List<ProjectSimple> findByCodeNotNullAndSalesId(Long salesId);
    @Query("select p from ProjectSimple p where p.code is not null and p.customerId = ?1")
    List<ProjectSimple> findByCodeNotNullAndCustomerId(Long customerId);


}
