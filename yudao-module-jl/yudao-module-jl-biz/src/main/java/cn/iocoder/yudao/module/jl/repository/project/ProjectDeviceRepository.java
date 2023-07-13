package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectDevice;
import org.springframework.data.jpa.repository.*;

/**
* ProjectDeviceRepository
*
*/
public interface ProjectDeviceRepository extends JpaRepository<ProjectDevice, Long>, JpaSpecificationExecutor<ProjectDevice> {

}
