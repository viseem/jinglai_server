package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.SupplySendInItem;
import org.springframework.data.jpa.repository.*;

/**
* SupplySendInItemRepository
*
*/
public interface SupplySendInItemRepository extends JpaRepository<SupplySendInItem, Long>, JpaSpecificationExecutor<SupplySendInItem> {

}
