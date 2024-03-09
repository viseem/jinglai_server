package cn.iocoder.yudao.module.jl.repository.contractfundlog;

import cn.iocoder.yudao.module.jl.entity.contractfundlog.ContractFundLog;
import cn.iocoder.yudao.module.jl.entity.projectfundlog.ProjectFundLog;
import org.springframework.data.jpa.repository.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
* ContractFundLogRepository
*
*/
public interface ContractFundLogRepository extends JpaRepository<ContractFundLog, Long>, JpaSpecificationExecutor<ContractFundLog> {
    @Query("select c from ContractFundLog c where c.status = ?1 and c.paidTime between ?2 and ?3 and c.salesId in ?4")
    List<ContractFundLog> findByStatusAndPaidTimeBetweenAndSalesIdIn(String status, LocalDateTime paidTimeStart, LocalDateTime paidTimeEnd, Collection<Long> salesIds);
    @Query("select c from ContractFundLog c where c.customerId = ?1")
    List<ContractFundLog> findByCustomerId(Long customerId);
    @Query("select count(c) from ContractFundLog c where c.status <> ?1")
    Integer countByStatusNot(String status);


    List<ContractFundLog> findAllByContractId(@NotNull(message = "合同id不能为空") Long contractId);

}
