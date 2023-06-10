package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.SupplySendIn;
import org.springframework.data.jpa.repository.*;

/**
* SupplySendInRepository
*
*/
public interface SupplySendInRepository extends JpaRepository<SupplySendIn, Long>, JpaSpecificationExecutor<SupplySendIn> {

}
