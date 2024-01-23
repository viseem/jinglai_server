package cn.iocoder.yudao.module.jl.repository.inventoryproductlog;

import cn.iocoder.yudao.module.jl.entity.inventoryproductlog.InventoryProductLog;
import org.springframework.data.jpa.repository.*;

/**
* InventoryProductLogRepository
*
*/
public interface InventoryProductLogRepository extends JpaRepository<InventoryProductLog, Long>, JpaSpecificationExecutor<InventoryProductLog> {

}
