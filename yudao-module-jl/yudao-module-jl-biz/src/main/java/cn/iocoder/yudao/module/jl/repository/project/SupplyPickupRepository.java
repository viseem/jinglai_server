package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.Procurement;
import cn.iocoder.yudao.module.jl.entity.project.SupplyPickup;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* SupplyPickupRepository
*
*/
public interface SupplyPickupRepository extends JpaRepository<SupplyPickup, Long>, JpaSpecificationExecutor<SupplyPickup> {

    @Query("select count(p) from SupplyPickup p where p.waitStoreIn = ?1")
    Integer countByWaitStoreIn(Boolean waitStoreIn);

    @Query("select count(p) from SupplyPickup p where p.waitCheckIn = ?1")
    Integer countByWaitCheckIn(Boolean waitCheckIn);

    @Query("select count(s) from SupplyPickup s where s.code like concat(?1, '%')")
    long countByCodeStartsWith(String code);
    @Query("select count(s) from SupplyPickup s where s.status = ?1")
    Integer countByStatus(String status);
    @Transactional
    @Modifying
    @Query("update SupplyPickup s set s.waitStoreIn = ?2 where s.id = ?1")
    int updateWaitStoreInById(Long id, Boolean waitStoreIn);

    SupplyPickup findFirstByOrderByIdDesc();

}
