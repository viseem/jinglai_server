package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectChargeitem;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

/**
* ProjectChargeitemRepository
*
*/
public interface ProjectChargeitemRepository extends JpaRepository<ProjectChargeitem, Long>, JpaSpecificationExecutor<ProjectChargeitem> {
    @Transactional
    @Modifying
    @Query("update ProjectChargeitem p set p.currentQuantity = p.quantity, p.currentSpec = p.spec, p.currentPrice = p.unitFee,p.deletedStatus=false " +
            "where p.quotationId = ?1")
    int initCurrentData(Long quotationId);
    @Transactional
    @Modifying
    @Query("update ProjectChargeitem p set p.deletedStatus = ?1 where p.projectCategoryId = ?2")
    int updateDeletedStatusByProjectCategoryId(Boolean deletedStatus, Long projectCategoryId);
    @Transactional
    @Modifying
    @Query("update ProjectChargeitem p set p.deletedStatus = ?1 where p.id = ?2")
    int updateDeletedStatusById(Boolean deletedStatus, Long id);
    @Query("select p from ProjectChargeitem p where p.projectCategoryId = ?1")
    List<ProjectChargeitem> findByProjectCategoryId(Long projectCategoryId);
    @Transactional
    @Modifying
    @Query("delete from ProjectChargeitem p where p.quotationId = ?1")
    int deleteByQuotationId(Long quotationId);
    @Query("select p from ProjectChargeitem p where p.quotationId = ?1  order by p.sort ASC")
    List<ProjectChargeitem> findByQuotationId(Long quotationId);
    @Transactional
    @Modifying
    @Query("update ProjectChargeitem p set p.deleted = ?1 where p.projectCategoryId = ?2")
    int updateDeletedByProjectCategoryId(Boolean deleted, Long projectCategoryId);
    @Transactional
    @Modifying
    @Query("delete from ProjectChargeitem p where p.projectCategoryId = ?1")
    int deleteByProjectCategoryId(Long projectCategoryId);
    @Query("select p from ProjectChargeitem p where p.scheduleId = ?1")
    List<ProjectChargeitem> findByScheduleId(Long scheduleId);

    @Query("select p from ProjectChargeitem p where p.projectId = ?1 order by p.sort ASC")
    List<ProjectChargeitem> findByProjectId(Long projectId);
    @Transactional
    @Modifying
    @Query("delete from ProjectChargeitem p where p.projectCategoryId in ?1")
    int deleteByProjectCategoryIdIn(Collection<Long> projectCategoryIds);

}
