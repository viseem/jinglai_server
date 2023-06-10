package cn.iocoder.yudao.module.jl.repository.inventory;

import cn.iocoder.yudao.module.jl.entity.inventory.InventoryContainer;
import org.springframework.data.jpa.repository.*;

/**
* InventoryContainerRepository
*
*/
public interface InventoryContainerRepository extends JpaRepository<InventoryContainer, Long>, JpaSpecificationExecutor<InventoryContainer> {

}
