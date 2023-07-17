package cn.iocoder.yudao.module.jl.repository.animal;

import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedOrder;
import org.springframework.data.jpa.repository.*;

/**
* AnimalFeedOrderRepository
*
*/
public interface AnimalFeedOrderRepository extends JpaRepository<AnimalFeedOrder, Long>, JpaSpecificationExecutor<AnimalFeedOrder> {

}
