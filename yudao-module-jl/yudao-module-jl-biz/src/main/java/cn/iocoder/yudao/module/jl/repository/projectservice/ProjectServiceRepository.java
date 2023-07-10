package cn.iocoder.yudao.module.jl.repository.projectservice;

import cn.iocoder.yudao.module.jl.entity.projectservice.ProjectService;
import org.springframework.data.jpa.repository.*;

/**
* ProjectServiceRepository
*
*/
public interface ProjectServiceRepository extends JpaRepository<ProjectService, Long>, JpaSpecificationExecutor<ProjectService> {

}
