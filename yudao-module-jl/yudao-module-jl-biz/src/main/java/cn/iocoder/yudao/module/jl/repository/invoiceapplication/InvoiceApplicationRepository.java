package cn.iocoder.yudao.module.jl.repository.invoiceapplication;

import cn.iocoder.yudao.module.jl.entity.invoiceapplication.InvoiceApplication;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* InvoiceApplicationRepository
*
*/
public interface InvoiceApplicationRepository extends JpaRepository<InvoiceApplication, Long>, JpaSpecificationExecutor<InvoiceApplication> {
    @Transactional
    @Modifying
    @Query("update InvoiceApplication i set i.status = ?1, i.auditId = ?2, i.auditName = ?3, i.auditMark = ?4 " +
            "where i.id = ?5")
    int updateStatusAndAuditIdAndAuditNameAndAuditMarkById(String status, Long auditId, String auditName, String auditMark, Long id);


}
