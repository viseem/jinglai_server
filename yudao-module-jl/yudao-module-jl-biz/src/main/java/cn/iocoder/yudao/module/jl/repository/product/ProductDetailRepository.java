package cn.iocoder.yudao.module.jl.repository.product;

import cn.iocoder.yudao.module.jl.entity.product.Product;
import cn.iocoder.yudao.module.jl.entity.product.ProductDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* ProductRepository
*
*/
public interface ProductDetailRepository extends JpaRepository<ProductDetail, Long>, JpaSpecificationExecutor<ProductDetail> {

}
