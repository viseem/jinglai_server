package cn.iocoder.yudao.module.jl.repository.crm;

import cn.iocoder.yudao.module.jl.entity.crm.Customer;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* CustomerRepository
*
*/
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    @Transactional
    @Modifying
    @Query("update Customer c set c.lastFollowupId = ?1 where c.id = ?2")
    void updateLastFollowupIdById(Long lastFollowupId, Long id);

}
