package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProcurementPayment;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* ProcurementPaymentRepository
*
*/
public interface ProcurementPaymentRepository extends JpaRepository<ProcurementPayment, Long>, JpaSpecificationExecutor<ProcurementPayment> {
    @Query("select p from ProcurementPayment p where p.projectId = ?1")
    List<ProcurementPayment> findByProjectId(Long projectId);
    @Transactional
    @Modifying
    @Query("delete from ProcurementPayment p where p.procurementId = ?1")
    void deleteByProcurementId(Long procurementId);

}
