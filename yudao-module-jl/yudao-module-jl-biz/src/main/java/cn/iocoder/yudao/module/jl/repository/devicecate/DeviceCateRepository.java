package cn.iocoder.yudao.module.jl.repository.devicecate;

import cn.iocoder.yudao.module.jl.entity.devicecate.DeviceCate;
import org.springframework.data.jpa.repository.*;

import java.util.Collection;
import java.util.List;

/**
* DeviceCateRepository
*
*/
public interface DeviceCateRepository extends JpaRepository<DeviceCate, Long>, JpaSpecificationExecutor<DeviceCate> {
    @Query("select d from DeviceCate d where d.parentId in ?1")
    List<DeviceCate> findByParentIdIn(Collection<Long> parentIds);


}
