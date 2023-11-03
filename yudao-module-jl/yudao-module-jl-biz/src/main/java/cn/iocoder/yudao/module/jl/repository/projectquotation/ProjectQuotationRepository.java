package cn.iocoder.yudao.module.jl.repository.projectquotation;

import cn.iocoder.yudao.module.jl.entity.projectquotation.ProjectQuotation;
import org.springframework.data.jpa.repository.*;

/**
* ProjectQuotationRepository
*
*/
public interface ProjectQuotationRepository extends JpaRepository<ProjectQuotation, Long>, JpaSpecificationExecutor<ProjectQuotation> {

}
