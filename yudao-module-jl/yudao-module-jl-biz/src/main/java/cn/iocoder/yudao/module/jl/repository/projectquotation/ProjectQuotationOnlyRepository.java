package cn.iocoder.yudao.module.jl.repository.projectquotation;

import cn.iocoder.yudao.module.jl.entity.projectquotation.ProjectQuotation;
import cn.iocoder.yudao.module.jl.entity.projectquotation.ProjectQuotationOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
* ProjectQuotationRepository
*
*/
public interface ProjectQuotationOnlyRepository extends JpaRepository<ProjectQuotationOnly, Long>, JpaSpecificationExecutor<ProjectQuotationOnly> {
    @Query("select p from ProjectQuotationOnly p " +
            "where p.updateTime between ?1 and ?2 and p.updater in ?3 and p.resultPrice > ?4")
    List<ProjectQuotationOnly> findByUpdateTimeBetweenAndUpdaterInAndResultPriceGreaterThan(LocalDateTime updateTimeStart, LocalDateTime updateTimeEnd, Long[] updaters, BigDecimal resultPrice);


}
