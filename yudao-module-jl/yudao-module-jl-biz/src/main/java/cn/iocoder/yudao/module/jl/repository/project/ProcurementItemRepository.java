package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProcurementItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* ProcurementItemRepository
*
*/
public interface ProcurementItemRepository extends JpaRepository<ProcurementItem, Long>, JpaSpecificationExecutor<ProcurementItem> {
    @Query("select p from ProcurementItem p where p.scheduleId = ?1")
    List<ProcurementItem> findByScheduleId(Long scheduleId);
    @Transactional
    @Query("select p from ProcurementItem p where p.procurementId = ?1 and p.projectSupplyId = ?2")
    @Nullable
    ProcurementItem findByProcurementIdAndProjectSupplyId(Long procurementId, Long projectSupplyId);


    @Transactional
    @Modifying
    @Query("delete from ProcurementItem p where p.procurementId = ?1")
    void deleteByProcurementId(Long procurementId);

}
