package cn.iocoder.yudao.module.jl.repository.inventory;

import cn.iocoder.yudao.module.jl.entity.inventory.InventoryStoreIn;
import org.springframework.data.jpa.repository.*;

/**
* InventoryStoreInRepository
*
*/
public interface InventoryStoreInRepository extends JpaRepository<InventoryStoreIn, Long>, JpaSpecificationExecutor<InventoryStoreIn> {

}
