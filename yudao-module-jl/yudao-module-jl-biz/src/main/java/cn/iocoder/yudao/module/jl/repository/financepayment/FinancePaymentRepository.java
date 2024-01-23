package cn.iocoder.yudao.module.jl.repository.financepayment;

import cn.iocoder.yudao.module.jl.entity.financepayment.FinancePayment;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* FinancePaymentRepository
*
*/
public interface FinancePaymentRepository extends JpaRepository<FinancePayment, Long>, JpaSpecificationExecutor<FinancePayment> {
    @Query("select f from FinancePayment f where f.type = ?1 and f.refId = ?2 and f.auditStatus = ?3")
    List<FinancePayment> findByTypeAndRefIdAndAuditStatus(String type, Long refId, String auditStatus);
    @Query("select count(f) from FinancePayment f where f.auditStatus <> ?1")
    Integer countByAuditStatusNot(String auditStatus);
    @Query("select count(f) from FinancePayment f where f.auditStatus = ?1")
    long countByAuditStatus(String auditStatus);
    @Transactional
    @Modifying
    @Query("update FinancePayment f set f.auditStatus = ?1 where f.id = ?2")
    int updateAuditStatusById(String auditStatus, Long id);
    @Transactional
    @Modifying
    @Query("update FinancePayment f set f.processInstanceId = ?1 where f.id = ?2")
    int updateProcessInstanceIdById(String processInstanceId, Long id);

}
