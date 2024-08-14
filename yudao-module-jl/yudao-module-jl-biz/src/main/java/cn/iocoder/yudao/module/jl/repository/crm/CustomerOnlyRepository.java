package cn.iocoder.yudao.module.jl.repository.crm;

import cn.iocoder.yudao.module.jl.entity.crm.CustomerOnly;
import cn.iocoder.yudao.module.jl.entity.crm.CustomerSimple;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
* CustomerRepository
*
*/
public interface CustomerOnlyRepository extends JpaRepository<CustomerOnly, Long>, JpaSpecificationExecutor<CustomerOnly> {
    @Query("select c from CustomerOnly c where c.id in ?1")
    List<CustomerOnly> findByIdIn(Collection<Long> ids);

}
