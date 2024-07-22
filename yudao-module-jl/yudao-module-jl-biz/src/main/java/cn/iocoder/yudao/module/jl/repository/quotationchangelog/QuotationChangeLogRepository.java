package cn.iocoder.yudao.module.jl.repository.quotationchangelog;

import cn.iocoder.yudao.module.jl.entity.quotationchangelog.QuotationChangeLog;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
* QuotationChangeLogRepository
*
*/
public interface QuotationChangeLogRepository extends JpaRepository<QuotationChangeLog, Long>, JpaSpecificationExecutor<QuotationChangeLog> {
    @Query("select q from QuotationChangeLog q where q.quotationId = ?1")
    List<QuotationChangeLog> findByQuotationId(Long quotationId);

}
