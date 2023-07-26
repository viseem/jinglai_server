package cn.iocoder.yudao.module.jl.repository.animal;

import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedStoreIn;
import org.springframework.data.jpa.repository.*;

/**
* AnimalFeedStoreInRepository
*
*/
public interface AnimalFeedStoreInRepository extends JpaRepository<AnimalFeedStoreIn, Long>, JpaSpecificationExecutor<AnimalFeedStoreIn> {

}
