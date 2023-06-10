package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.SupplyPickup;
import org.springframework.data.jpa.repository.*;

/**
* SupplyPickupRepository
*
*/
public interface SupplyPickupRepository extends JpaRepository<SupplyPickup, Long>, JpaSpecificationExecutor<SupplyPickup> {

}
