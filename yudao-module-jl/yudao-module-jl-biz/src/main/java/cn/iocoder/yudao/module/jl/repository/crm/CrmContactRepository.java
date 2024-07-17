package cn.iocoder.yudao.module.jl.repository.crm;

import cn.iocoder.yudao.module.jl.entity.crm.CrmContact;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* CrmContactRepository
*
*/
public interface CrmContactRepository extends JpaRepository<CrmContact, Long>, JpaSpecificationExecutor<CrmContact> {


}
