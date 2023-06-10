package cn.iocoder.yudao.module.jl.repository.inventory;

import cn.iocoder.yudao.module.jl.entity.inventory.ProductIn;
import org.springframework.data.jpa.repository.*;

/**
* ProductInRepository
*
*/
public interface ProductInRepository extends JpaRepository<ProductIn, Long>, JpaSpecificationExecutor<ProductIn> {

}
