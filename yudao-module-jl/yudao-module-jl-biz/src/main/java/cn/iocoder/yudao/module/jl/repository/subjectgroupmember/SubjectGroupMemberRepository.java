package cn.iocoder.yudao.module.jl.repository.subjectgroupmember;

import cn.iocoder.yudao.module.jl.entity.subjectgroupmember.SubjectGroupMember;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
* SubjectGroupMemberRepository
*
*/
public interface SubjectGroupMemberRepository extends JpaRepository<SubjectGroupMember, Long>, JpaSpecificationExecutor<SubjectGroupMember> {
    List<SubjectGroupMember> findByGroupId(Long groupId);

}
