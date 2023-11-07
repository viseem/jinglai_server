package cn.iocoder.yudao.module.jl.repository.crmsubjectgroup;

import cn.iocoder.yudao.module.jl.entity.crmsubjectgroup.CrmSubjectGroup;
import org.springframework.data.jpa.repository.*;

/**
* CrmSubjectGroupRepository
*
*/
public interface CrmSubjectGroupRepository extends JpaRepository<CrmSubjectGroup, Long>, JpaSpecificationExecutor<CrmSubjectGroup> {
    @Query("select c from CrmSubjectGroup c where c.name = ?1")
    CrmSubjectGroup findByName(String name);

}
