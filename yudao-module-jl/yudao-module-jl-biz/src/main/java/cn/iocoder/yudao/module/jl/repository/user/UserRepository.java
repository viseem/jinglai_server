package cn.iocoder.yudao.module.jl.repository.user;

import cn.iocoder.yudao.module.jl.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
* UserRepository
*
*/
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    @Transactional
    @Modifying
    @Query("update User u set u.wxCpId = ?1 where u.id = ?2")
    int updateUserWxCpId(String wxCpId, Long id);

    @Query("select u from User u where u.deptId in ?1")
    List<User> findByDeptIdIn(Collection<Long> deptIds);


}
