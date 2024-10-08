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
    @Query("select u from User u where u.mobile = ?1")
    User findByMobile(String mobile);
    @Query("select u from User u where u.deptId = ?1")
    List<User> findByDeptId(Long deptId);

    @Query("select u from User u where u.deptId = 119")
    List<User> findFinanceUsers();

    @Query("select u from User u where u.nickname = ?1")
    List<User> findByNickname(String nickname);

    @Transactional
    @Modifying
    @Query("update User u set u.wxCpId = ?1 where u.id = ?2")
    int updateUserWxCpId(String wxCpId, Long id);

    @Query("select u from User u where u.deptId in ?1")
    List<User> findByDeptIdIn(Collection<Long> deptIds);


}
