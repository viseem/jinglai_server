package cn.iocoder.yudao.module.jl.repository.purchasecontract;

import cn.iocoder.yudao.module.jl.entity.purchasecontract.PurchaseContract;
import org.springframework.data.jpa.repository.*;

/**
* PurchaseContractRepository
*
*/
public interface PurchaseContractRepository extends JpaRepository<PurchaseContract, Long>, JpaSpecificationExecutor<PurchaseContract> {

}
