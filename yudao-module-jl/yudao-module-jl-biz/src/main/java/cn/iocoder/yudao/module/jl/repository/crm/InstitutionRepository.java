package cn.iocoder.yudao.module.jl.repository.crm;

import cn.iocoder.yudao.module.jl.entity.crm.Institution;
import org.springframework.data.jpa.repository.*;

/**
* InstitutionRepository
*
*/
public interface InstitutionRepository extends JpaRepository<Institution, Long>, JpaSpecificationExecutor<Institution> {
    @Query("select i from Institution i where i.name = ?1")
    Institution findByName(String name);

}
