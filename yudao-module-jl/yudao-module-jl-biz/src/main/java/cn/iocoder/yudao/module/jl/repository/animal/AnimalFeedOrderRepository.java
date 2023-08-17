package cn.iocoder.yudao.module.jl.repository.animal;

import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* AnimalFeedOrderRepository
*
*/
public interface AnimalFeedOrderRepository extends JpaRepository<AnimalFeedOrder, Long>, JpaSpecificationExecutor<AnimalFeedOrder> {
    @Transactional
    @Modifying
    @Query("update AnimalFeedOrder a set a.stage = ?1 where a.id = ?2")
    int updateStageById(String stage, Long id);
    @Transactional
    @Modifying
    @Query("update AnimalFeedOrder a set a.processInstanceId = ?1 where a.id = ?2")
    int updateProcessInstanceIdById(String processInstanceId, Long id);

    AnimalFeedOrder findFirstByOrderByIdDesc();
}
