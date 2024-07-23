package cn.iocoder.yudao.module.jl.repository.projectsettlement;

import cn.iocoder.yudao.module.jl.entity.projectsettlement.ProjectSettlement;
import org.springframework.data.jpa.repository.*;

/**
* ProjectSettlementRepository
*
*/
public interface ProjectSettlementRepository extends JpaRepository<ProjectSettlement, Long>, JpaSpecificationExecutor<ProjectSettlement> {

}
