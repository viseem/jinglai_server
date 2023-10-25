package cn.iocoder.yudao.module.jl.repository.contractfundlog;

import cn.iocoder.yudao.module.jl.entity.contractfundlog.ContractFundLog;
import org.springframework.data.jpa.repository.*;

/**
* ContractFundLogRepository
*
*/
public interface ContractFundLogRepository extends JpaRepository<ContractFundLog, Long>, JpaSpecificationExecutor<ContractFundLog> {

}
