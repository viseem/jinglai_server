package cn.iocoder.yudao.module.jl.repository.salesgroupmember;

import cn.iocoder.yudao.module.jl.entity.salesgroupmember.SalesGroupMember;
import org.springframework.data.jpa.repository.*;

import java.util.Collection;
import java.util.List;

/**
* SalesGroupMemberRepository
*
*/
public interface SalesGroupMemberRepository extends JpaRepository<SalesGroupMember, Long>, JpaSpecificationExecutor<SalesGroupMember> {
    @Query("select s from SalesGroupMember s where s.groupId in ?1")
    List<SalesGroupMember> findByGroupIdIn(Long[] groupIds);
    @Query("select s from SalesGroupMember s where s.groupId = ?1")
    List<SalesGroupMember> findByGroupId(Long groupId);

}
