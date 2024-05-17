package cn.iocoder.yudao.module.jl.repository.productcate;

import cn.iocoder.yudao.module.jl.entity.productcate.ProductCate;
import cn.iocoder.yudao.module.jl.entity.productcate.ProductCateDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* ProductCateRepository
*
*/
public interface ProductCateDetailRepository extends JpaRepository<ProductCateDetail, Long>, JpaSpecificationExecutor<ProductCateDetail> {

}
