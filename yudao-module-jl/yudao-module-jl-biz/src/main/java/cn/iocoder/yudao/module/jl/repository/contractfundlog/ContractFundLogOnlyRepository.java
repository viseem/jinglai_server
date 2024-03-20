package cn.iocoder.yudao.module.jl.repository.contractfundlog;

import cn.iocoder.yudao.module.jl.entity.contractfundlog.ContractFundLog;
import cn.iocoder.yudao.module.jl.entity.contractfundlog.ContractFundLogOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
* ContractFundLogRepository
*
*/
public interface ContractFundLogOnlyRepository extends JpaRepository<ContractFundLogOnly, Long>, JpaSpecificationExecutor<ContractFundLogOnly> {
    @Query("select c from ContractFundLogOnly c where c.status = ?1 and c.paidTime between ?2 and ?3 and c.salesId in ?4")
    List<ContractFundLogOnly> findByStatusAndPaidTimeBetweenAndSalesIdIn(String status, LocalDateTime paidTimeStart, LocalDateTime paidTimeEnd, Collection<Long> salesIds);
    @Query("select c from ContractFundLogOnly c where c.customerId = ?1")
    List<ContractFundLogOnly> findByCustomerId(Long customerId);
    @Query("select count(c) from ContractFundLogOnly c where c.status <> ?1")
    Integer countByStatusNot(String status);


    List<ContractFundLogOnly> findAllByContractId(@NotNull(message = "合同id不能为空") Long contractId);

}
