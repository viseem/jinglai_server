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
    @Query("select count(c) from CommonTask c where c.quotationId = ?1 and c.status <> ?2")
    long countByQuotationIdAndStatusNot(Long quotationId, Integer status);
    @Query("select count(c) from CommonTask c where c.quotationId = ?1 and c.status = ?2")
    long countByQuotationIdAndStatus(Long quotationId, Integer status);
    @Query("select c from CommonTask c where c.parentId = ?1")
    List<CommonTask> findByParentId(Long parentId);
    @Transactional
    @Modifying
    @Query("update CommonTask c set c.experIds = ?1 where c.id = ?2")
    int updateExperIdsById(String experIds, Long id);
    @Transactional
    @Modifying
    @Query("update CommonTask c set c.status = ?1 where c.projectId = ?2 and c.status = ?3")
    int updateStatusByProjectIdAndStatus(Integer status, Long projectId, Integer status1);

    @Transactional
    @Modifying
    @Query("update CommonTask c set c.status = ?1 where c.quotationId = ?2 and c.status = ?3")
    int updateStatusByQuotationIdAndStatus(Integer status, Long quotationId, Integer status1);

    @Query("select c from CommonTask c where c.quotationId = ?1")
    List<CommonTask> findByQuotationId(Long quotationId);
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
