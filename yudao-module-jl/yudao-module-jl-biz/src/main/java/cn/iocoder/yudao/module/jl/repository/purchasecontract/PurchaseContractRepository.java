package cn.iocoder.yudao.module.jl.repository.purchasecontract;

import cn.iocoder.yudao.module.jl.entity.purchasecontract.PurchaseContract;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
* PurchaseContractRepository
*
*/
public interface PurchaseContractRepository extends JpaRepository<PurchaseContract, Long>, JpaSpecificationExecutor<PurchaseContract> {
    @Transactional
    @Modifying
    @Query("update PurchaseContract p set p.acceptTime = ?1 where p.id = ?2")
    int updateAcceptTimeById(LocalDateTime acceptTime, Long id);
    @Transactional
    @Modifying
    @Query("update PurchaseContract p set p.processInstanceId = ?1 where p.id = ?2")
    int updateProcessInstanceIdById(String processInstanceId, Long id);
    @Transactional
    @Modifying
    @Query("update PurchaseContract p set p.status = ?1 where p.id = ?2")
    int updateStatusById(String status, Long id);

}
