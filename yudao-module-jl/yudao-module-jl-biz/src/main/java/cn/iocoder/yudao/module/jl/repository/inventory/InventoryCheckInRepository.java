package cn.iocoder.yudao.module.jl.repository.inventory;

import cn.iocoder.yudao.module.jl.entity.inventory.InventoryCheckIn;
import org.springframework.data.jpa.repository.*;

/**
* InventoryCheckInRepository
*
*/
public interface InventoryCheckInRepository extends JpaRepository<InventoryCheckIn, Long>, JpaSpecificationExecutor<InventoryCheckIn> {

}
