package cn.iocoder.yudao.module.jl.repository.crm;

import cn.iocoder.yudao.module.jl.entity.crm.Customer;
import cn.iocoder.yudao.module.jl.entity.crm.CustomerOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
* CustomerRepository
*
*/
public interface CustomerSimpleRepository extends JpaRepository<CustomerOnly, Long>, JpaSpecificationExecutor<CustomerOnly> {
    @Query("select c from CustomerOnly c where c.phone = ?1")
    CustomerOnly findByPhone(String phone);
    @Query("select count(c) from Customer c where c.creator = ?1 and (c.toCustomer = false or c.toCustomer is null)")
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
