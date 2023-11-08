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
    @Query("select p from ProcurementItem p where p.supplierId = ?1")
    List<ProcurementItem> findBySupplierId(Long supplierId);
    @Query("select p from ProcurementItem p where p.projectId = ?1")
    List<ProcurementItem> findByProjectId(Long projectId);
    @Transactional
    @Modifying
    @Query("update ProcurementItem p set p.status = ?1 where p.procurementId = ?2")
    int updateStatusByProcurementId(String status, Long procurementId);
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
