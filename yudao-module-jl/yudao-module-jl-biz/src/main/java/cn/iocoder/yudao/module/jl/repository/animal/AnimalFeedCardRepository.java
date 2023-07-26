package cn.iocoder.yudao.module.jl.repository.animal;

import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedCard;
import org.springframework.data.jpa.repository.*;

/**
* AnimalFeedCardRepository
*
*/
public interface AnimalFeedCardRepository extends JpaRepository<AnimalFeedCard, Long>, JpaSpecificationExecutor<AnimalFeedCard> {

}
