package cn.iocoder.yudao.module.jl.repository.subjectgroup;

import cn.iocoder.yudao.module.jl.entity.subjectgroup.SubjectGroup;
import org.springframework.data.jpa.repository.*;

/**
* SubjectGroupRepository
*
*/
public interface SubjectGroupRepository extends JpaRepository<SubjectGroup, Long>, JpaSpecificationExecutor<SubjectGroup> {

}
