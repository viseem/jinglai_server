package cn.iocoder.yudao.module.jl.repository.inventory;

import cn.iocoder.yudao.module.jl.entity.inventory.SupplyOut;
import org.springframework.data.jpa.repository.*;

/**
* SupplyOutRepository
*
*/
public interface SupplyOutRepository extends JpaRepository<SupplyOut, Long>, JpaSpecificationExecutor<SupplyOut> {

}
