package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProcurementPayment;
import org.springframework.data.jpa.repository.*;

/**
* ProcurementPaymentRepository
*
*/
public interface ProcurementPaymentRepository extends JpaRepository<ProcurementPayment, Long>, JpaSpecificationExecutor<ProcurementPayment> {

}
