package cn.iocoder.yudao.module.jl.repository.contractfundlog;

import cn.iocoder.yudao.module.jl.entity.contractfundlog.ContractFundLog;
import cn.iocoder.yudao.module.jl.entity.projectfundlog.ProjectFundLog;
import org.springframework.data.jpa.repository.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
* ContractFundLogRepository
*
*/
public interface ContractFundLogRepository extends JpaRepository<ContractFundLog, Long>, JpaSpecificationExecutor<ContractFundLog> {


    List<ContractFundLog> findAllByContractId(@NotNull(message = "合同id不能为空") Long contractId);

}
