package cn.iocoder.yudao.module.jl.repository.crm;

import cn.iocoder.yudao.module.jl.entity.crm.Saleslead;
import org.springframework.data.jpa.repository.*;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

/**
* SalesleadRepository
*
*/
public interface SalesleadRepository extends JpaRepository<Saleslead, Long>, JpaSpecificationExecutor<Saleslead> {
    @Query("select count(s) from Saleslead s where s.creator = ?1 and (s.status != ?2 or s.status is null)")
    Integer countByCreatorAndStatusNot(Long creator, Integer status);
    @Query("select count(s) from Saleslead s where s.status != ?1")
    Integer countByStatusNot(Integer status);
    @Transactional
    @Modifying
    @Query("update Saleslead s set s.lastFollowUpId = ?2 where s.id = ?1")
    void updateLastFollowUpIdById( Long id, @NonNull Long lastFollowUpId);

    @Transactional
    @Modifying
    @Query("update Saleslead s set s.quotation = ?2 where s.id = ?1")
    void updateQuotationById(Long id, Long quotationId);

}
