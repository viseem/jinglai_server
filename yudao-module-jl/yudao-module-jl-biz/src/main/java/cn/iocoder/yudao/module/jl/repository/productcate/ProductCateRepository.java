package cn.iocoder.yudao.module.jl.repository.productcate;

import cn.iocoder.yudao.module.jl.entity.productcate.ProductCate;
import org.springframework.data.jpa.repository.*;

/**
* ProductCateRepository
*
*/
public interface ProductCateRepository extends JpaRepository<ProductCate, Long>, JpaSpecificationExecutor<ProductCate> {

}
