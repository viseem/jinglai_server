package cn.iocoder.yudao.module.jl.repository.shipwarehouse;

import cn.iocoder.yudao.module.jl.entity.shipwarehouse.ShipWarehouse;
import org.springframework.data.jpa.repository.*;

/**
* ShipWarehouseRepository
*
*/
public interface ShipWarehouseRepository extends JpaRepository<ShipWarehouse, Long>, JpaSpecificationExecutor<ShipWarehouse> {

}
