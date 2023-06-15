package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.Procurement;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* ProcurementRepository
*
*/
public interface ProcurementRepository extends JpaRepository<Procurement, Long>, JpaSpecificationExecutor<Procurement> {
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

}
