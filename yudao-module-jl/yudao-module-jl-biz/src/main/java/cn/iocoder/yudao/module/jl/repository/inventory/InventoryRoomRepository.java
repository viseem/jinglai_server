package cn.iocoder.yudao.module.jl.repository.inventory;

import cn.iocoder.yudao.module.jl.entity.inventory.InventoryRoom;
import org.springframework.data.jpa.repository.*;

/**
* InventoryRoomRepository
*
*/
public interface InventoryRoomRepository extends JpaRepository<InventoryRoom, Long>, JpaSpecificationExecutor<InventoryRoom> {

}
