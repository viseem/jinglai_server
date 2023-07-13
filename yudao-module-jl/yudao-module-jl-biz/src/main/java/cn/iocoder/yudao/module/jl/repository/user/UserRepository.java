package cn.iocoder.yudao.module.jl.repository.user;

import cn.iocoder.yudao.module.jl.entity.user.User;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
* UserRepository
*
*/
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {


}
