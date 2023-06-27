package cn.iocoder.yudao.module.jl.repository.inventory;

import cn.iocoder.yudao.module.jl.entity.inventory.InventoryOptAttachment;
import org.springframework.data.jpa.repository.*;

/**
* InventoryOptAttachmentRepository
*
*/
public interface InventoryOptAttachmentRepository extends JpaRepository<InventoryOptAttachment, Long>, JpaSpecificationExecutor<InventoryOptAttachment> {

}
