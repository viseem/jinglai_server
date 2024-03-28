package cn.iocoder.yudao.module.jl.repository.purchasecontract;

import cn.iocoder.yudao.module.jl.entity.purchasecontract.PurchaseContract;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* PurchaseContractRepository
*
*/
public interface PurchaseContractRepository extends JpaRepository<PurchaseContract, Long>, JpaSpecificationExecutor<PurchaseContract> {
    @Transactional
    @Modifying
    @Query("update PurchaseContract p set p.status = ?1 where p.id = ?2")
    int updateStatusById(String status, Long id);

}
