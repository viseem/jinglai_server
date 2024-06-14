package cn.iocoder.yudao.module.jl.repository.subjectgroupmember;

import cn.iocoder.yudao.module.jl.entity.subjectgroupmember.SubjectGroupMember;
import cn.iocoder.yudao.module.jl.entity.subjectgroupmember.SubjectGroupMemberOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* SubjectGroupMemberRepository
*
*/
public interface SubjectGroupMemberRepositoryOnly extends JpaRepository<SubjectGroupMemberOnly, Long>, JpaSpecificationExecutor<SubjectGroupMemberOnly> {
    @Query("select s from SubjectGroupMember s where s.userId = ?1")
    List<SubjectGroupMemberOnly> findByUserId(Long userId);
    List<SubjectGroupMemberOnly> findByGroupId(Long groupId);

}
