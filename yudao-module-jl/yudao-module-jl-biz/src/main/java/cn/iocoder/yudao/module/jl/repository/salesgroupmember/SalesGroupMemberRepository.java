package cn.iocoder.yudao.module.jl.repository.salesgroupmember;

import cn.iocoder.yudao.module.jl.entity.salesgroupmember.SalesGroupMember;
import org.springframework.data.jpa.repository.*;

/**
* SalesGroupMemberRepository
*
*/
public interface SalesGroupMemberRepository extends JpaRepository<SalesGroupMember, Long>, JpaSpecificationExecutor<SalesGroupMember> {

}
