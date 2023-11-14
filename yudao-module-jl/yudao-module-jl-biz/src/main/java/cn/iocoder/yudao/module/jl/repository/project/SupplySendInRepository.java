package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.Procurement;
import cn.iocoder.yudao.module.jl.entity.project.SupplySendIn;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* SupplySendInRepository
*
*/
public interface SupplySendInRepository extends JpaRepository<SupplySendIn, Long>, JpaSpecificationExecutor<SupplySendIn> {
    @Query("select count(s) from SupplySendIn s where s.code like concat(?1, '%')")
    long countByCodeStartsWith(String code);
    @Query("select count(s) from SupplySendIn s where s.status = ?1")
    Integer countByStatus(String status);
    @Transactional
    @Modifying
    @Query("update SupplySendIn s set s.waitStoreIn = ?2 where s.id = ?1")
    int updateWaitStoreInById(Long id, Boolean waitStoreIn);

    SupplySendIn findFirstByOrderByIdDesc();

}
