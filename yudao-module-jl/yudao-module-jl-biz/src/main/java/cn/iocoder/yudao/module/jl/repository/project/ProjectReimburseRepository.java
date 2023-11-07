package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectReimburse;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
* ProjectReimburseRepository
*
*/
public interface ProjectReimburseRepository extends JpaRepository<ProjectReimburse, Long>, JpaSpecificationExecutor<ProjectReimburse> {
    @Transactional
    @Modifying
    @Query("update ProjectReimburse p set p.paidPrice = ?1 where p.id = ?2")
    int updatePaidPriceById(BigDecimal paidPrice, Long id);
    @Query("select p from ProjectReimburse p where p.scheduleId = ?1")
    List<ProjectReimburse> findByScheduleId(Long scheduleId);

    @Query("select p from ProjectReimburse p where p.projectId = ?1")
    List<ProjectReimburse> findByProjectId(Long projectId);

}
