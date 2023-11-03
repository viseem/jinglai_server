package cn.iocoder.yudao.module.jl.repository.contractinvoicelog;

import cn.iocoder.yudao.module.jl.entity.contractinvoicelog.ContractInvoiceLog;
import org.springframework.data.jpa.repository.*;

/**
* ContractInvoiceLogRepository
*
*/
public interface ContractInvoiceLogRepository extends JpaRepository<ContractInvoiceLog, Long>, JpaSpecificationExecutor<ContractInvoiceLog> {
    @Query("select count(c) from ContractInvoiceLog c where c.status <> ?1")
    Integer countByStatusNot(String status);

}
