package cn.iocoder.yudao.module.jl.repository.inventory;

import cn.iocoder.yudao.module.jl.entity.inventory.SupplyOut;
import org.springframework.data.jpa.repository.*;

import javax.persistence.criteria.CriteriaBuilder;

/**
* SupplyOutRepository
*
*/
public interface SupplyOutRepository extends JpaRepository<SupplyOut, Long>, JpaSpecificationExecutor<SupplyOut> {
    @Query("select count(s) from SupplyOut s where s.status <> ?1 or s.status is null")
    Integer countByStatusNot(String status);

}
