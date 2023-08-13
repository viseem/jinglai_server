package cn.iocoder.yudao.module.jl.repository.approval;

import cn.iocoder.yudao.module.jl.entity.approval.ApprovalProgress;
import org.springframework.data.jpa.repository.*;

/**
* ApprovalProgressRepository
*
*/
public interface ApprovalProgressRepository extends JpaRepository<ApprovalProgress, Long>, JpaSpecificationExecutor<ApprovalProgress> {

}
