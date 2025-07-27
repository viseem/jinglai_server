package cn.iocoder.yudao.module.jl.repository.product;

import cn.iocoder.yudao.module.jl.entity.product.Product;
import org.springframework.data.jpa.repository.*;

/**
* ProductRepository
*
*/
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

    /**
     * 查询最大的sort值
     */
    @Query("SELECT MAX(p.sort) FROM Product p WHERE p.sort IS NOT NULL")
    Integer findMaxSort();

}
