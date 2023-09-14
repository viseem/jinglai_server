package cn.iocoder.yudao.module.jl.repository.feedback;

import cn.iocoder.yudao.module.jl.entity.feedback.Feedback;
import org.springframework.data.jpa.repository.*;

/**
* FeedbackRepository
*
*/
public interface FeedbackRepository extends JpaRepository<Feedback, Long>, JpaSpecificationExecutor<Feedback> {

}
