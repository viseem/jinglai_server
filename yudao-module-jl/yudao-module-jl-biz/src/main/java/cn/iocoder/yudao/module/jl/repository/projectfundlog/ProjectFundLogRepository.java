package cn.iocoder.yudao.module.jl.repository.projectfundlog;

import cn.iocoder.yudao.module.jl.entity.projectfundlog.ProjectFundLog;
import org.springframework.data.jpa.repository.*;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
* ProjectFundLogRepository
*
*/
public interface ProjectFundLogRepository extends JpaRepository<ProjectFundLog, Long>, JpaSpecificationExecutor<ProjectFundLog> {
    @Query("select p from ProjectFundLog p where p.customerId = ?1")
    List<ProjectFundLog> findByCustomerId(Long customerId);
    @Query("select p from ProjectFundLog p where p.projectFundId = ?1")
    ProjectFundLog findByProjectFundId(Long projectFundId);

    List<ProjectFundLog> findAllByProjectFundId(@NotNull(message = "项目款项主表id不能为空") Long projectFundId);
}
