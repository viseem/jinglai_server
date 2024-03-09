package cn.iocoder.yudao.module.jl.repository.salesgroup;

import cn.iocoder.yudao.module.jl.entity.salesgroup.SalesGroup;
import org.springframework.data.jpa.repository.*;

/**
* SalesGroupRepository
*
*/
public interface SalesGroupRepository extends JpaRepository<SalesGroup, Long>, JpaSpecificationExecutor<SalesGroup> {

}
