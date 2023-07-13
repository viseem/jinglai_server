package cn.iocoder.yudao.module.jl.repository.template;

import cn.iocoder.yudao.module.jl.entity.template.TemplateContract;
import org.springframework.data.jpa.repository.*;

/**
* TemplateContractRepository
*
*/
public interface TemplateContractRepository extends JpaRepository<TemplateContract, Long>, JpaSpecificationExecutor<TemplateContract> {

}
