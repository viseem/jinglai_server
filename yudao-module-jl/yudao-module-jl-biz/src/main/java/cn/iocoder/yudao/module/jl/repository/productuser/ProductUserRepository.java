package cn.iocoder.yudao.module.jl.repository.productuser;

import cn.iocoder.yudao.module.jl.entity.productuser.ProductUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* ProductUserRepository
*
*/
public interface ProductUserRepository extends JpaRepository<ProductUser, Long>, JpaSpecificationExecutor<ProductUser> {
    @Transactional
    @Modifying
    @Query("delete from ProductUser p where p.productId = ?1")
    int deleteByProductId(Long productId);

}
