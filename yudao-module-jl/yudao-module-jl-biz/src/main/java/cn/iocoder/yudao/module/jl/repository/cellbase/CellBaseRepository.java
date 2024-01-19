package cn.iocoder.yudao.module.jl.repository.cellbase;

import cn.iocoder.yudao.module.jl.entity.cellbase.CellBase;
import org.springframework.data.jpa.repository.*;

/**
* CellBaseRepository
*
*/
public interface CellBaseRepository extends JpaRepository<CellBase, Long>, JpaSpecificationExecutor<CellBase> {

}
