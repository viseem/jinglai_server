package cn.iocoder.yudao.module.jl.repository.template;

import cn.iocoder.yudao.module.jl.entity.template.TemplateProjectPlan;
import org.springframework.data.jpa.repository.*;

/**
* TemplateProjectPlanRepository
*
*/
public interface TemplateProjectPlanRepository extends JpaRepository<TemplateProjectPlan, Long>, JpaSpecificationExecutor<TemplateProjectPlan> {

}
