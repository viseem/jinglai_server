package cn.iocoder.yudao.module.jl.repository.visitappointment;

import cn.iocoder.yudao.module.jl.entity.visitappointment.VisitAppointment;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* VisitAppointmentRepository
*
*/
public interface VisitAppointmentRepository extends JpaRepository<VisitAppointment, Long>, JpaSpecificationExecutor<VisitAppointment> {
    @Transactional
    @Modifying
    @Query("update VisitAppointment v set v.deviceLogId = ?1 where v.id = ?2")
    int updateDeviceLogIdById(Long deviceLogId, Long id);

}
