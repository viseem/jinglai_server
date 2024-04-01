package cn.iocoder.yudao.module.jl.repository.productsop;

import cn.iocoder.yudao.module.jl.entity.productsop.ProductSop;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* ProductSopRepository
*
*/
public interface ProductSopRepository extends JpaRepository<ProductSop, Long>, JpaSpecificationExecutor<ProductSop> {
    @Transactional
    @Modifying
    @Query("delete from ProductSop p where p.productId = ?1")
    int deleteByProductId(Long productId);

}
