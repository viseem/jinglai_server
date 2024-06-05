package cn.iocoder.yudao.module.jl.repository.commontask;

import cn.iocoder.yudao.module.jl.entity.commontask.CommonTask;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
* CommonTaskRepository
*
*/
public interface CommonTaskRepository extends JpaRepository<CommonTask, Long>, JpaSpecificationExecutor<CommonTask> {
    @Query("select c from CommonTask c where c.projectCategoryId = ?1")
    List<CommonTask> findByProjectCategoryId(Long projectCategoryId);
    @Query("select count(c) from CommonTask c where c.userId = ?1 and c.status not in ?2")
    Integer countByUserIdAndStatusNotIn(Long userId, Integer[] statuses);
    @Transactional
    @Modifying
    @Query("update CommonTask c set c.status = ?1 where c.id = ?2")
    int updateStatusById(Integer status, Long id);
    @Transactional
    @Modifying
    @Query("update CommonTask c set c.status = ?1 where c.id = ?2 and c.status <> ?3")
    int updateStatusByIdAndStatusNot(Integer status, Long id, Integer status1);

}
