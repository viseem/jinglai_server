package cn.iocoder.yudao.module.jl.repository.inventory;

import cn.iocoder.yudao.module.jl.entity.inventory.ProductInItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* ProductInItemRepository
*
*/
public interface ProductInItemRepository extends JpaRepository<ProductInItem, Long>, JpaSpecificationExecutor<ProductInItem> {
    @Transactional
    @Modifying
    @Query("delete from ProductInItem p where p.productInId = ?1")
    void deleteByProductInId(Long productInId);

}
