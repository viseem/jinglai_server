package cn.iocoder.yudao.module.jl.repository.visitappointment;

import cn.iocoder.yudao.module.jl.entity.visitappointment.VisitAppointment;
import org.springframework.data.jpa.repository.*;

/**
* VisitAppointmentRepository
*
*/
public interface VisitAppointmentRepository extends JpaRepository<VisitAppointment, Long>, JpaSpecificationExecutor<VisitAppointment> {

}
