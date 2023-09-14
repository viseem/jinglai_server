package cn.iocoder.yudao.module.jl.repository.animal;

import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedLog;
import org.springframework.data.jpa.repository.*;

import java.util.Optional;

/**
* AnimalFeedLogRepository
*
*/
public interface AnimalFeedLogRepository extends JpaRepository<AnimalFeedLog, Long>, JpaSpecificationExecutor<AnimalFeedLog> {


    Optional<AnimalFeedLog> findFirstByFeedOrderIdOrderByIdDesc(Long feedOrderId);

}
