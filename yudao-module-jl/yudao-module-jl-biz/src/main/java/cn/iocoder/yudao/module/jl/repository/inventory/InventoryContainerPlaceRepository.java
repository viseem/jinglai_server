package cn.iocoder.yudao.module.jl.repository.inventory;

import cn.iocoder.yudao.module.jl.entity.inventory.InventoryContainerPlace;
import org.springframework.data.jpa.repository.*;

/**
* InventoryContainerPlaceRepository
*
*/
public interface InventoryContainerPlaceRepository extends JpaRepository<InventoryContainerPlace, Long>, JpaSpecificationExecutor<InventoryContainerPlace> {

}
