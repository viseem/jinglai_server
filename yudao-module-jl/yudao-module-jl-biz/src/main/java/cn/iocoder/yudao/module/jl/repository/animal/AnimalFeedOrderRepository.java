package cn.iocoder.yudao.module.jl.repository.animal;

import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedOrder;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* AnimalFeedOrderRepository
*
*/
public interface AnimalFeedOrderRepository extends JpaRepository<AnimalFeedOrder, Long>, JpaSpecificationExecutor<AnimalFeedOrder> {
    @Query("select count(a) from AnimalFeedOrder a where a.code like concat(?1, '%')")
    long countByCodeStartsWith(String code);
    @Query("select a from AnimalFeedOrder a where a.code like concat(?1, '%')")
    AnimalFeedOrder findByCodeStartsWith(String code);
    @Query("select a from AnimalFeedOrder a where a.stage = ?1")
    List<AnimalFeedOrder> findByStage(String stage);
    @Query("select count(a) from AnimalFeedOrder a where a.stage = ?1")
    long countByStage(String stage);
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
