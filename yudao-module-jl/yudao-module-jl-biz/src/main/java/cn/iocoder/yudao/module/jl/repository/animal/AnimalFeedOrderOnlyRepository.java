package cn.iocoder.yudao.module.jl.repository.animal;

import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedOrder;
import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedOrderOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* AnimalFeedOrderRepository
*
*/
public interface AnimalFeedOrderOnlyRepository extends JpaRepository<AnimalFeedOrderOnly, Long>, JpaSpecificationExecutor<AnimalFeedOrderOnly> {
    @Transactional
    @Modifying
    @Query("update AnimalFeedOrder a set a.location = ?1, a.locationCode = ?2 where a.id = ?3")
    int updateLocationAndLocationCodeById(String location, String locationCode, Long id);
    @Query("select count(a) from AnimalFeedOrder a where a.code like concat(?1, '%')")
    long countByCodeStartsWith(String code);
    @Query("select a from AnimalFeedOrder a where a.code like concat(?1, '%')")
    AnimalFeedOrderOnly findByCodeStartsWith(String code);
    @Query("select a from AnimalFeedOrder a where a.stage = ?1")
    List<AnimalFeedOrderOnly> findByStage(String stage);
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

    AnimalFeedOrderOnly findFirstByOrderByIdDesc();
}
