package cn.iocoder.yudao.module.jl.repository.product;

import cn.iocoder.yudao.module.jl.entity.product.Product;
import org.springframework.data.jpa.repository.*;

/**
* ProductRepository
*
*/
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {

}
