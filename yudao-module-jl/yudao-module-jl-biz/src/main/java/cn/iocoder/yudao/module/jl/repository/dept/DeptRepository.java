package cn.iocoder.yudao.module.jl.repository.dept;

import cn.iocoder.yudao.module.jl.entity.dept.Dept;
import org.springframework.data.jpa.repository.*;

/**
* DeptRepository
*
*/
public interface DeptRepository extends JpaRepository<Dept, Long>, JpaSpecificationExecutor<Dept> {

}
