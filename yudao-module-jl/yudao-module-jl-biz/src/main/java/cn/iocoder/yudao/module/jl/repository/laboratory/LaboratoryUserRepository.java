package cn.iocoder.yudao.module.jl.repository.laboratory;

import cn.iocoder.yudao.module.jl.entity.laboratory.LaboratoryUser;
import org.springframework.data.jpa.repository.*;

/**
* LaboratoryUserRepository
*
*/
public interface LaboratoryUserRepository extends JpaRepository<LaboratoryUser, Long>, JpaSpecificationExecutor<LaboratoryUser> {

}
