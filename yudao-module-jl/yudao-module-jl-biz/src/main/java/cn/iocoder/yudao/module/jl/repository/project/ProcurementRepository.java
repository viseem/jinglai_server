package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.Procurement;
import cn.iocoder.yudao.module.jl.entity.project.ProcurementPayment;
import cn.iocoder.yudao.module.jl.entity.project.Project;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* ProcurementRepository
*
*/
public interface ProcurementRepository extends JpaRepository<Procurement, Long>, JpaSpecificationExecutor<Procurement> {
    @Query("select count(p) from Procurement p where p.code like concat(?1, '%')")
    long countByCodeStartsWith(String code);
    @Query("select p from Procurement p where p.code like concat(?1, '%')")
    Procurement findByCodeStartsWith(String code);
    @Query("select count(p) from Procurement p where p.waitCheckIn = ?1")
    Integer countByWaitCheckIn(Boolean waitCheckIn);
    @Query("select count(p) from Procurement p where p.waitStoreIn = ?1")
    Integer countByWaitStoreIn(Boolean waitStoreIn);

    @Query("select count(p) from Procurement p where p.status = ?1")
    Integer countByStatus(String status);
    @Transactional
    @Modifying
    @Query("update Procurement p set p.processInstanceId = ?1 where p.id = ?2")
    int updateProcessInstanceIdById(String processInstanceId, Long id);

    @Query("select p from Procurement p where p.scheduleId = ?1")
    List<Procurement> findByScheduleId(Long scheduleId);

    @Transactional
    @Modifying
    @Query("update Procurement p set p.shipmentCodes = ?1 where p.id = ?2")
    int updateShipmentCodesById(Long id, String shipmentCodes);

    @Transactional
    @Modifying
    @Query("update Procurement p set p.waitStoreIn = ?2 where p.id = ?1")
    void updateWaitStoreInById(Long id, Boolean waitStoreIn);

    @Transactional
    @Modifying
    @Query("update Procurement p set p.waitCheckIn = ?2 where p.id = ?1")
    void updateWaitCheckInById(Long id, Boolean waitCheckIn);

    @Transactional
    @Modifying
    @Query("update Procurement p set p.status = ?2 where p.id = ?1")
    int updateStatusById(Long id, String status);

    @Query("select count(p) from Procurement p where p.projectId = ?1")
    long countByProjectId(Long projectId);

    Procurement findFirstByOrderByIdDesc();

}
