package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProcurementItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* ProcurementItemRepository
*
*/
public interface ProcurementItemRepository extends JpaRepository<ProcurementItem, Long>, JpaSpecificationExecutor<ProcurementItem> {
    @Transactional
    @Modifying
    @Query("delete from ProcurementItem p where p.procurementId = ?1")
    void deleteByProcurementId(Long procurementId);

}
