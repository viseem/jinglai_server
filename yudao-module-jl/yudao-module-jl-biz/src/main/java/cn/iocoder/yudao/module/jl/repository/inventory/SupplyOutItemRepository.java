package cn.iocoder.yudao.module.jl.repository.inventory;

import cn.iocoder.yudao.module.jl.entity.inventory.SupplyOutItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* SupplyOutItemRepository
*
*/
public interface SupplyOutItemRepository extends JpaRepository<SupplyOutItem, Long>, JpaSpecificationExecutor<SupplyOutItem> {
    @Transactional
    @Modifying
    @Query("delete from SupplyOutItem s where s.supplyOutId = ?1")
    int deleteBySupplyOutId(Long supplyOutId);

}
