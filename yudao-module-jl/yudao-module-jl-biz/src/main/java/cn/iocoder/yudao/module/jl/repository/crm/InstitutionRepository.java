package cn.iocoder.yudao.module.jl.repository.crm;

import cn.iocoder.yudao.module.jl.entity.crm.Institution;
import org.springframework.data.jpa.repository.*;

import java.util.Collection;
import java.util.List;

/**
* InstitutionRepository
*
*/
public interface InstitutionRepository extends JpaRepository<Institution, Long>, JpaSpecificationExecutor<Institution> {
    @Query("select i from Institution i where i.name in ?1")
    List<Institution> findByNameIn(Collection<String> names);
    @Query("select i from Institution i where i.name like ?1")
    List<Institution> findByNameLike(String name);
    @Query("select i from Institution i where i.name = ?1")
    Institution findByName(String name);

}
