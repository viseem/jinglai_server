package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProcurementItem;
import org.springframework.data.jpa.repository.*;

/**
* ProcurementItemRepository
*
*/
public interface ProcurementItemRepository extends JpaRepository<ProcurementItem, Long>, JpaSpecificationExecutor<ProcurementItem> {

}
