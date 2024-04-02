package cn.iocoder.yudao.module.jl.repository.product;

import cn.iocoder.yudao.module.jl.entity.product.Product;
import cn.iocoder.yudao.module.jl.entity.product.ProductOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

/**
* ProductRepository
*
*/
public interface ProductOnlyRepository extends JpaRepository<ProductOnly, Long>, JpaSpecificationExecutor<ProductOnly> {
    @Query("select count(p) from ProductOnly p where p.cateId = ?1")
    long countByCateId(Long cateId);

}
