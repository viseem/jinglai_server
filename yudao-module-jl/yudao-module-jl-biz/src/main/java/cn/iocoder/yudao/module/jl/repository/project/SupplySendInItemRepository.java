package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.SupplySendInItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* SupplySendInItemRepository
*
*/
public interface SupplySendInItemRepository extends JpaRepository<SupplySendInItem, Long>, JpaSpecificationExecutor<SupplySendInItem> {
    @Query("select s from SupplySendInItem s where s.supplySendInId = ?1 and s.projectSupplyId = ?2")
    SupplySendInItem findBySupplySendInIdAndProjectSupplyId(Long supplySendInId, Long projectSupplyId);

    @Transactional
    @Modifying
    @Query("delete from SupplySendInItem s where s.supplySendInId = ?1")
    void deleteBySupplySendInId(Long supplySendInId);

}
