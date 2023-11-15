package cn.iocoder.yudao.module.jl.repository.contractinvoicelog;

import cn.iocoder.yudao.module.jl.entity.contractinvoicelog.ContractInvoiceLog;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
* ContractInvoiceLogRepository
*
*/
public interface ContractInvoiceLogRepository extends JpaRepository<ContractInvoiceLog, Long>, JpaSpecificationExecutor<ContractInvoiceLog> {
    @Query("select c from ContractInvoiceLog c where c.contractId = ?1")
    List<ContractInvoiceLog> findByContractId(Long contractId);
    @Query("select count(c) from ContractInvoiceLog c where c.status <> ?1")
    Integer countByStatusNot(String status);
    @Query("select count(c) from ContractInvoiceLog c where c.status <> ?1 or c.priceStatus <> ?2")
    Integer countByStatusNotOrPriceStatusNot(String status,Integer priceStatus);
}
