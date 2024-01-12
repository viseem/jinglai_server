package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectDevice;
import org.springframework.data.jpa.repository.*;

/**
* ProjectDeviceRepository
*
*/
public interface ProjectDeviceRepository extends JpaRepository<ProjectDevice, Long>, JpaSpecificationExecutor<ProjectDevice> {
    @Query("select p from ProjectDevice p where p.projectId = ?1 and p.deviceId = ?2")
    ProjectDevice findByProjectIdAndDeviceId(Long projectId, Long deviceId);

}
