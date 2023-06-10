package cn.iocoder.yudao.module.jl.repository.inventory;

import cn.iocoder.yudao.module.jl.entity.inventory.ProductSendItem;
import org.springframework.data.jpa.repository.*;

/**
* ProductSendItemRepository
*
*/
public interface ProductSendItemRepository extends JpaRepository<ProductSendItem, Long>, JpaSpecificationExecutor<ProductSendItem> {

}
