package cn.iocoder.yudao.module.jl.repository.laboratory;

import cn.iocoder.yudao.module.jl.entity.laboratory.LaboratoryLab;
import org.springframework.data.jpa.repository.*;

/**
* LaboratoryLabRepository
*
*/
public interface LaboratoryLabRepository extends JpaRepository<LaboratoryLab, Long>, JpaSpecificationExecutor<LaboratoryLab> {

}
