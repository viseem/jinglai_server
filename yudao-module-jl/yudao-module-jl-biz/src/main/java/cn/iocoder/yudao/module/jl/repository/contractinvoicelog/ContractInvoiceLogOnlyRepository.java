package cn.iocoder.yudao.module.jl.repository.contractinvoicelog;

import cn.iocoder.yudao.module.jl.entity.contractinvoicelog.ContractInvoiceLog;
import cn.iocoder.yudao.module.jl.entity.contractinvoicelog.ContractInvoiceLogOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

/**
* ContractInvoiceLogRepository
*
*/
public interface ContractInvoiceLogOnlyRepository extends JpaRepository<ContractInvoiceLogOnly, Long>, JpaSpecificationExecutor<ContractInvoiceLogOnly> {
    @Query("select c from ContractInvoiceLogOnly c where c.projectId = ?1")
    List<ContractInvoiceLogOnly> findByProjectId(Long projectId);
    @Query("select c from ContractInvoiceLogOnly c where c.customerId = ?1")
    List<ContractInvoiceLogOnly> findByCustomerId(Long customerId);
    @Query("select c from ContractInvoiceLogOnly c where c.priceStatus = ?1 and c.salesId in ?2")
    List<ContractInvoiceLogOnly> findByPriceStatusAndSalesIdIn(String priceStatus, Long[] salesIds);
    @Query("select c from ContractInvoiceLogOnly c where c.contractId = ?1")
    List<ContractInvoiceLogOnly> findByContractId(Long contractId);
    @Query("select count(c) from ContractInvoiceLogOnly c where c.status <> ?1")
    Integer countByStatusNot(String status);
    @Query("select count(c) from ContractInvoiceLogOnly c where c.status <> ?1 or c.priceStatus <> ?2")
    Integer countByStatusNotOrPriceStatusNot(String status,String priceStatus);

    @Query("select c from ContractInvoiceLogOnly c where c.status = ?1 and c.date between ?2 and ?3 and c.salesId in ?4")
    List<ContractInvoiceLogOnly> findByStatusAndPaidTimeBetweenAndSalesIdIn(String status, LocalDateTime paidTimeStart, LocalDateTime paidTimeEnd, Long[] salesIds);
}
