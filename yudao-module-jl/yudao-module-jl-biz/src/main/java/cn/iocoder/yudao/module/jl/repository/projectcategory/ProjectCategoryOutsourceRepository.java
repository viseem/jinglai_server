package cn.iocoder.yudao.module.jl.repository.projectcategory;

import cn.iocoder.yudao.module.jl.entity.projectcategory.ProjectCategoryOutsource;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
* ProjectCategoryOutsourceRepository
*
*/
public interface ProjectCategoryOutsourceRepository extends JpaRepository<ProjectCategoryOutsource, Long>, JpaSpecificationExecutor<ProjectCategoryOutsource> {
    @Transactional
    @Modifying
    @Query("update ProjectCategoryOutsource p set p.paidPrice = ?1 where p.id = ?2")
    int updatePaidPriceById(BigDecimal paidPrice, Long id);
    @Query("select p from ProjectCategoryOutsource p where p.scheduleId = ?1")
    List<ProjectCategoryOutsource> findByScheduleId(Long scheduleId);

    @Query("select p from ProjectCategoryOutsource p where p.projectId = ?1")
    List<ProjectCategoryOutsource> findByProjectId(Long projectId);
}
