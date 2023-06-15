package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProcurementShipment;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* ProcurementShipmentRepository
*
*/
public interface ProcurementShipmentRepository extends JpaRepository<ProcurementShipment, Long>, JpaSpecificationExecutor<ProcurementShipment> {
    @Transactional
    @Modifying
    @Query("delete from ProcurementShipment p where p.procurementId = ?1")
    int deleteByProcurementId(Long procurementId);

}
