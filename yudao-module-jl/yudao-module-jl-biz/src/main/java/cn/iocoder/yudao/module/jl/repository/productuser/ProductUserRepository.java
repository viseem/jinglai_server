package cn.iocoder.yudao.module.jl.repository.productuser;

import cn.iocoder.yudao.module.jl.entity.productuser.ProductUser;
import org.springframework.data.jpa.repository.*;

/**
* ProductUserRepository
*
*/
public interface ProductUserRepository extends JpaRepository<ProductUser, Long>, JpaSpecificationExecutor<ProductUser> {

}
