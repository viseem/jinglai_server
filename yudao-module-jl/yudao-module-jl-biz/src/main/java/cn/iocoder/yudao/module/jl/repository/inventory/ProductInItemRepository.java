package cn.iocoder.yudao.module.jl.repository.inventory;

import cn.iocoder.yudao.module.jl.entity.inventory.ProductInItem;
import org.springframework.data.jpa.repository.*;

/**
* ProductInItemRepository
*
*/
public interface ProductInItemRepository extends JpaRepository<ProductInItem, Long>, JpaSpecificationExecutor<ProductInItem> {

}
