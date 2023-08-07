package cn.iocoder.yudao.module.jl.repository.approval;

import cn.iocoder.yudao.module.jl.entity.approval.Approval;
import org.springframework.data.jpa.repository.*;

/**
* ApprovalRepository
*
*/
public interface ApprovalRepository extends JpaRepository<Approval, Long>, JpaSpecificationExecutor<Approval> {

}
