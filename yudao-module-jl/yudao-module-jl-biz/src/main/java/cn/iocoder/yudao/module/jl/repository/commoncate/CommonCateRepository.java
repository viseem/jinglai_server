package cn.iocoder.yudao.module.jl.repository.commoncate;

import cn.iocoder.yudao.module.jl.entity.commoncate.CommonCate;
import org.springframework.data.jpa.repository.*;

/**
* CommonCateRepository
*
*/
public interface CommonCateRepository extends JpaRepository<CommonCate, Long>, JpaSpecificationExecutor<CommonCate> {

}
