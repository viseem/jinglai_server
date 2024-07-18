package cn.iocoder.yudao.module.jl.repository.quotationchangelog;

import cn.iocoder.yudao.module.jl.entity.quotationchangelog.QuotationChangeLog;
import org.springframework.data.jpa.repository.*;

/**
* QuotationChangeLogRepository
*
*/
public interface QuotationChangeLogRepository extends JpaRepository<QuotationChangeLog, Long>, JpaSpecificationExecutor<QuotationChangeLog> {

}
