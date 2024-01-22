package cn.iocoder.yudao.module.jl.repository.projectquotation;

import cn.iocoder.yudao.module.jl.entity.projectquotation.ProjectQuotation;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
* ProjectQuotationRepository
*
*/
public interface ProjectQuotationRepository extends JpaRepository<ProjectQuotation, Long>, JpaSpecificationExecutor<ProjectQuotation> {
    @Transactional
    @Modifying
    @Query("update ProjectQuotation p set p.originPrice = ?1 where p.id = ?2")
    int updateOriginPriceById(BigDecimal originPrice, Long id);
    @Transactional
    @Modifying
    @Query("update ProjectQuotation p set p.discount = ?1 where p.id = ?2")
    int updateDiscountById(Integer discount, Long id);
    @Query("select p from ProjectQuotation p where p.projectId = ?1")
    List<ProjectQuotation> findByProjectId(Long projectId);
    @Transactional
    @Modifying
    @Query("update ProjectQuotation p set p.planText = ?1 where p.id = ?2")
    int updatePlanTextById(String planText, Long id);

}
