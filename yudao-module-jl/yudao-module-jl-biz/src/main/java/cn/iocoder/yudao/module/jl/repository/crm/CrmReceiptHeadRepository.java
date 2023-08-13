package cn.iocoder.yudao.module.jl.repository.crm;

import cn.iocoder.yudao.module.jl.entity.crm.CrmReceiptHead;
import org.springframework.data.jpa.repository.*;

/**
* CrmReceiptHeadRepository
*
*/
public interface CrmReceiptHeadRepository extends JpaRepository<CrmReceiptHead, Long>, JpaSpecificationExecutor<CrmReceiptHead> {

}
