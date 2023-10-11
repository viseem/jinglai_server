package cn.iocoder.yudao.module.jl.repository.crm;

import cn.iocoder.yudao.module.jl.entity.crm.Customer;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
* CustomerRepository
*
*/
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {
    @Query("select c from Customer c where c.phone = ?1")
    Customer findByPhone(String phone);
    @Query("select count(c) from Customer c where c.salesId = ?1 and (c.toCustomer = false or c.toCustomer is null)")
    Integer countByNotToCustomerAndCreator(Long creator);
    @Transactional
    @Modifying
    @Query("update Customer c set c.toCustomer = ?1 where c.id = ?2")
    int updateToCustomerById(Boolean toCustomer, Long id);
    @Transactional
    @Modifying
    @Query("update Customer c set c.lastSalesleadId = ?2 where c.id = ?1")
    int updateLastSalesleadIdById(Long id,Long lastSalesleadId );
    @Transactional
    @Modifying
    @Query("update Customer c set c.lastFollowupId = ?1 where c.id = ?2")
    void updateLastFollowupIdById(Long lastFollowupId, Long id);

}
