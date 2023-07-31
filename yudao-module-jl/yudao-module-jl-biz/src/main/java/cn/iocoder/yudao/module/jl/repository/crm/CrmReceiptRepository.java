package cn.iocoder.yudao.module.jl.repository.crm;

import cn.iocoder.yudao.module.jl.entity.crm.CrmReceipt;
import cn.iocoder.yudao.module.jl.entity.project.ProjectConstract;
import org.springframework.data.jpa.repository.*;

/**
* CrmReceiptRepository
*
*/
public interface CrmReceiptRepository extends JpaRepository<CrmReceipt, Long>, JpaSpecificationExecutor<CrmReceipt> {

    CrmReceipt findFirstByOrderByIdDesc();
}
