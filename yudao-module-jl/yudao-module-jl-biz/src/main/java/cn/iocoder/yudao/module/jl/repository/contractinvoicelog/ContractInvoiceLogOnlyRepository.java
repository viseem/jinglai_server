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

    @Query("select c from ContractInvoiceLogOnly c where c.applicationId = ?1")
    List<ContractInvoiceLogOnly> findByApplicationId(Long applicationId);
    @Query("select c from ContractInvoiceLogOnly c where c.projectId = ?1")
    List<ContractInvoiceLogOnly> findByProjectId(Long projectId);

    @Query("select count(c) from ContractInvoiceLogOnly c where c.status <> ?1")
    Integer countByStatusNot(String status);

    @Query("select c from ContractInvoiceLogOnly c where c.status = ?1 and c.date between ?2 and ?3 and c.salesId in ?4")
    List<ContractInvoiceLogOnly> findByStatusAndPaidTimeBetweenAndSalesIdIn(String status, LocalDateTime paidTimeStart, LocalDateTime paidTimeEnd, Long[] salesIds);

    @Query("select c from ContractInvoiceLogOnly c where c.status <> ?1 and c.date between ?2 and ?3 and c.salesId in ?4")
    List<ContractInvoiceLogOnly> findByStatusNotAndPaidTimeBetweenAndSalesIdIn(String status, LocalDateTime paidTimeStart, LocalDateTime paidTimeEnd, Long[] salesIds);

    @Query("select c from ContractInvoiceLogOnly c where  c.date between ?1 and ?2 and c.salesId in ?3")
    List<ContractInvoiceLogOnly> findPaidTimeBetweenAndSalesIdIn( LocalDateTime paidTimeStart, LocalDateTime paidTimeEnd, Long[] salesIds);
}
