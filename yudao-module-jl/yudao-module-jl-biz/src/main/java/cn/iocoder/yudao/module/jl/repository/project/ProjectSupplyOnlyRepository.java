package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectSupply;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSupplyOnly;
import org.hibernate.annotations.OrderBy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
* ProjectSupplyRepository
*
*/
public interface ProjectSupplyOnlyRepository extends JpaRepository<ProjectSupplyOnly, Long>, JpaSpecificationExecutor<ProjectSupplyOnly> {
    @Query("select p from ProjectSupplyOnly p where p.quotationId = ?1")
    List<ProjectSupplyOnly> findByQuotationId(Long quotationId);

}
