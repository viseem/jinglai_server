package cn.iocoder.yudao.module.jl.repository.suppliertag;

import cn.iocoder.yudao.module.jl.entity.suppliertag.SupplierTag;
import org.springframework.data.jpa.repository.*;

/**
* SupplierTagRepository
*
*/
public interface SupplierTagRepository extends JpaRepository<SupplierTag, Long>, JpaSpecificationExecutor<SupplierTag> {

}
