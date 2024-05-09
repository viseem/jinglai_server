package cn.iocoder.yudao.module.jl.repository.inventorystorelog;

import cn.iocoder.yudao.module.jl.entity.inventorystorelog.InventoryStoreLog;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
* InventoryStoreLogRepository
*
*/
public interface InventoryStoreLogRepository extends JpaRepository<InventoryStoreLog, Long>, JpaSpecificationExecutor<InventoryStoreLog> {
    @Query("select i from InventoryStoreLog i where i.sourceItemId = ?1")
    List<InventoryStoreLog> findBySourceItemId(Long sourceItemId);

}
