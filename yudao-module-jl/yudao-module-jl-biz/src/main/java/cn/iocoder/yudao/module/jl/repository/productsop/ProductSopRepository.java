package cn.iocoder.yudao.module.jl.repository.productsop;

import cn.iocoder.yudao.module.jl.entity.productsop.ProductSop;
import org.springframework.data.jpa.repository.*;

/**
* ProductSopRepository
*
*/
public interface ProductSopRepository extends JpaRepository<ProductSop, Long>, JpaSpecificationExecutor<ProductSop> {

}
