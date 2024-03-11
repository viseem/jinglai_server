package cn.iocoder.yudao.module.jl.repository.picollaborationitem;

import cn.iocoder.yudao.module.jl.entity.picollaborationitem.PiCollaborationItem;
import org.springframework.data.jpa.repository.*;

/**
* PiCollaborationItemRepository
*
*/
public interface PiCollaborationItemRepository extends JpaRepository<PiCollaborationItem, Long>, JpaSpecificationExecutor<PiCollaborationItem> {

}
