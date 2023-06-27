package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.SupplyPickup;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* SupplyPickupRepository
*
*/
public interface SupplyPickupRepository extends JpaRepository<SupplyPickup, Long>, JpaSpecificationExecutor<SupplyPickup> {
    @Transactional
    @Modifying
    @Query("update SupplyPickup s set s.waitStoreIn = ?2 where s.id = ?1")
    int updateWaitStoreInById(Long id, Boolean waitStoreIn);

}
