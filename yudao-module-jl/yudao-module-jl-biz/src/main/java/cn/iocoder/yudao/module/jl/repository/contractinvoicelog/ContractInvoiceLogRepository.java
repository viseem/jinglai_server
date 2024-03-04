package cn.iocoder.yudao.module.jl.repository.contractinvoicelog;

import cn.iocoder.yudao.module.jl.entity.contractinvoicelog.ContractInvoiceLog;
import org.springframework.data.jpa.repository.*;

import java.util.Collection;
import java.util.List;

/**
* ContractInvoiceLogRepository
*
*/
public interface ContractInvoiceLogRepository extends JpaRepository<ContractInvoiceLog, Long>, JpaSpecificationExecutor<ContractInvoiceLog> {
    @Query("select c from ContractInvoiceLog c where c.customerId = ?1")
    List<ContractInvoiceLog> findByCustomerId(Long customerId);
    @Query("select c from ContractInvoiceLog c where c.priceStatus = ?1 and c.salesId in ?2")
    List<ContractInvoiceLog> findByPriceStatusAndSalesIdIn(String priceStatus, Long[] salesIds);
    @Query("select c from ContractInvoiceLog c where c.contractId = ?1")
    List<ContractInvoiceLog> findByContractId(Long contractId);
    @Query("select count(c) from ContractInvoiceLog c where c.status <> ?1")
    Integer countByStatusNot(String status);
    @Query("select count(c) from ContractInvoiceLog c where c.status <> ?1 or c.priceStatus <> ?2")
    Integer countByStatusNotOrPriceStatusNot(String status,String priceStatus);
}
