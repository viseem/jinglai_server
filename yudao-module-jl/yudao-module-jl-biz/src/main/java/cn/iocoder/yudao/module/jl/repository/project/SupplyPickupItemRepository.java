package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.SupplyPickupItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* SupplyPickupItemRepository
*
*/
public interface SupplyPickupItemRepository extends JpaRepository<SupplyPickupItem, Long>, JpaSpecificationExecutor<SupplyPickupItem> {
    @Query("select s from SupplyPickupItem s where s.supplyPickupId = ?1 and s.projectSupplyId = ?2")
    SupplyPickupItem findBySupplyPickupIdAndProjectSupplyId(Long supplyPickupId, Long projectSupplyId);
    @Transactional
    @Modifying
    @Query("delete from SupplyPickupItem s where s.supplyPickupId = ?1")
    void deleteBySupplyPickupId(Long supplyPickupId);

}