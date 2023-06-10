package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.Procurement;
import org.springframework.data.jpa.repository.*;

/**
* ProcurementRepository
*
*/
public interface ProcurementRepository extends JpaRepository<Procurement, Long>, JpaSpecificationExecutor<Procurement> {

}
