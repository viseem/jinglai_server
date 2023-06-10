package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProcurementShipment;
import org.springframework.data.jpa.repository.*;

/**
* ProcurementShipmentRepository
*
*/
public interface ProcurementShipmentRepository extends JpaRepository<ProcurementShipment, Long>, JpaSpecificationExecutor<ProcurementShipment> {

}
