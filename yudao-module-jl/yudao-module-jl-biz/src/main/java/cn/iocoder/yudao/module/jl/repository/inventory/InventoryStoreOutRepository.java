package cn.iocoder.yudao.module.jl.repository.inventory;

import cn.iocoder.yudao.module.jl.entity.inventory.InventoryStoreOut;
import org.springframework.data.jpa.repository.*;

/**
* InventoryStoreOutRepository
*
*/
public interface InventoryStoreOutRepository extends JpaRepository<InventoryStoreOut, Long>, JpaSpecificationExecutor<InventoryStoreOut> {

}
