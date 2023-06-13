package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectFeedback;
import org.springframework.data.jpa.repository.*;

/**
* ProjectFeedbackRepository
*
*/
public interface ProjectFeedbackRepository extends JpaRepository<ProjectFeedback, Long>, JpaSpecificationExecutor<ProjectFeedback> {

}
