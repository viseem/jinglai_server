package cn.iocoder.yudao.module.jl.repository.subjectgroupmember;

import cn.iocoder.yudao.module.jl.entity.subjectgroupmember.SubjectGroupMember;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
* SubjectGroupMemberRepository
*
*/
public interface SubjectGroupMemberRepository extends JpaRepository<SubjectGroupMember, Long>, JpaSpecificationExecutor<SubjectGroupMember> {
    @Query("select s from SubjectGroupMember s where s.userId = ?1")
    SubjectGroupMember findByUserId(Long userId);
    List<SubjectGroupMember> findByGroupId(Long groupId);

}
