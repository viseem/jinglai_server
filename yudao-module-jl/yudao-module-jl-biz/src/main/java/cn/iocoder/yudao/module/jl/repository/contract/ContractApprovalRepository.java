package cn.iocoder.yudao.module.jl.repository.contract;

import cn.iocoder.yudao.module.jl.entity.contract.ContractApproval;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* ContractApprovalRepository
*
*/
public interface ContractApprovalRepository extends JpaRepository<ContractApproval, Long>, JpaSpecificationExecutor<ContractApproval> {
    @Transactional
    @Modifying
    @Query("update ContractApproval c set c.processInstanceId = ?1 where c.id = ?2")
    int updateProcessInstanceIdById(String processInstanceId, Long id);

}
