package cn.iocoder.yudao.module.jl.repository.inventory;

import cn.iocoder.yudao.module.jl.entity.inventory.SupplyOutItem;
import org.springframework.data.jpa.repository.*;

/**
* SupplyOutItemRepository
*
*/
public interface SupplyOutItemRepository extends JpaRepository<SupplyOutItem, Long>, JpaSpecificationExecutor<SupplyOutItem> {

}
