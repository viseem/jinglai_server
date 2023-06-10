package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.SupplyPickupItem;
import org.springframework.data.jpa.repository.*;

/**
* SupplyPickupItemRepository
*
*/
public interface SupplyPickupItemRepository extends JpaRepository<SupplyPickupItem, Long>, JpaSpecificationExecutor<SupplyPickupItem> {

}
