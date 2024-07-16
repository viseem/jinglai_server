package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProcurementItem;
import cn.iocoder.yudao.module.jl.entity.project.ProcurementItemOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
* ProcurementItemRepository
*
*/
public interface ProcurementItemOnlyRepository extends JpaRepository<ProcurementItemOnly, Long>, JpaSpecificationExecutor<ProcurementItemOnly> {
    @Transactional
    @Modifying
    @Query("update ProcurementItemOnly p set p.inedQuantity = ?1, p.outedQuantity = ?2 where p.id = ?3")
    int updateInedQuantityAndOutedQuantityById(BigDecimal inedQuantity, BigDecimal outedQuantity, Long id);
    @Transactional
    @Modifying
    @Query("update ProcurementItemOnly p set p.inedQuantity = ?1 where p.id = ?2")
    int updateInedQuantityById(BigDecimal inedQuantity, Long id);

}
