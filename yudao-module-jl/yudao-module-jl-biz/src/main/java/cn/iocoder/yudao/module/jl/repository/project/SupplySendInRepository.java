package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.SupplySendIn;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* SupplySendInRepository
*
*/
public interface SupplySendInRepository extends JpaRepository<SupplySendIn, Long>, JpaSpecificationExecutor<SupplySendIn> {
    @Transactional
    @Modifying
    @Query("update SupplySendIn s set s.waitStoreIn = ?2 where s.id = ?1")
    int updateWaitStoreInById(Long id, Boolean waitStoreIn);

}
