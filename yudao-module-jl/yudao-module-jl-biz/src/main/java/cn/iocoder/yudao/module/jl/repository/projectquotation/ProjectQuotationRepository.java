package cn.iocoder.yudao.module.jl.repository.projectquotation;

import cn.iocoder.yudao.module.jl.entity.projectquotation.ProjectQuotation;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* ProjectQuotationRepository
*
*/
public interface ProjectQuotationRepository extends JpaRepository<ProjectQuotation, Long>, JpaSpecificationExecutor<ProjectQuotation> {
    @Transactional
    @Modifying
    @Query("update ProjectQuotation p set p.planText = ?1 where p.id = ?2")
    int updatePlanTextById(String planText, Long id);

}