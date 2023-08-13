package cn.iocoder.yudao.module.jl.repository.approval;

import cn.iocoder.yudao.module.jl.entity.approval.Approval;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* ApprovalRepository
*
*/
public interface ApprovalRepository extends JpaRepository<Approval, Long>, JpaSpecificationExecutor<Approval> {
    @Transactional
    @Modifying
    @Query("update Approval a set a.status = ?1 where a.id = ?2")
    int updateStatusById(String status, Long id);

}
