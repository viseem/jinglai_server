package cn.iocoder.yudao.module.jl.repository.user;

import cn.iocoder.yudao.module.jl.entity.user.User;
import org.springframework.data.jpa.repository.*;

import java.util.Collection;
import java.util.List;

/**
* UserRepository
*
*/
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    @Query("select u from User u where u.deptId in ?1")
    List<User> findByDeptIdIn(Collection<Long> deptIds);


}
