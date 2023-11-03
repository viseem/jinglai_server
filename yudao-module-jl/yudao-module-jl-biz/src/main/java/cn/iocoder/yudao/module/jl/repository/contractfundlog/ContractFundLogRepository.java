package cn.iocoder.yudao.module.jl.repository.contractfundlog;

import cn.iocoder.yudao.module.jl.entity.contractfundlog.ContractFundLog;
import cn.iocoder.yudao.module.jl.entity.projectfundlog.ProjectFundLog;
import org.springframework.data.jpa.repository.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
* ContractFundLogRepository
*
*/
public interface ContractFundLogRepository extends JpaRepository<ContractFundLog, Long>, JpaSpecificationExecutor<ContractFundLog> {
    @Query("select count(c) from ContractFundLog c where c.status <> ?1")
    Integer countByStatusNot(String status);


    List<ContractFundLog> findAllByContractId(@NotNull(message = "合同id不能为空") Long contractId);

}
