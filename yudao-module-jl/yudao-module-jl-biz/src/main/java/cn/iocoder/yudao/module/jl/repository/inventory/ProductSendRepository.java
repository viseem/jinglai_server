package cn.iocoder.yudao.module.jl.repository.inventory;

import cn.iocoder.yudao.module.jl.entity.inventory.ProductSend;
import org.springframework.data.jpa.repository.*;

/**
* ProductSendRepository
*
*/
public interface ProductSendRepository extends JpaRepository<ProductSend, Long>, JpaSpecificationExecutor<ProductSend> {

}
