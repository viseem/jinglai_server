package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectSupply;
import org.hibernate.annotations.OrderBy;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
* ProjectSupplyRepository
*
*/
public interface ProjectSupplyRepository extends JpaRepository<ProjectSupply, Long>, JpaSpecificationExecutor<ProjectSupply> {
    @Transactional
    @Modifying
    @Query("update ProjectSupply p set p.currentBrand = p.brand, p.currentCatalogNumber = p.catalogNumber, p.currentPrice = p.unitFee, p.currentSpec = p.spec, p.currentQuantity = p.quantity,p.deletedStatus=false " +
            "where p.quotationId = ?1")
    int initCurrentData( Long quotationId);
    @Transactional
    @Modifying
    @Query("update ProjectSupply p set p.deletedStatus = ?1 where p.id = ?2")
    int updateDeletedStatusById(Boolean deletedStatus, Long id);
    @Transactional
    @Modifying
    @Query("update ProjectSupply p set p.brand = ?1, p.catalogNumber = ?2, p.receiveRoomName = ?3, p.procurementSpec = ?4, p.salePrice = ?5, p.originPrice = ?6 " +
            "where p.id = ?7")
    int updateProcurementRelationValueById(String brand, String catalogNumber, String receiveRoomName, String procurementSpec, BigDecimal salePrice, BigDecimal originPrice, Long id);
    @Query("select p from ProjectSupply p where p.projectCategoryId = ?1")
    List<ProjectSupply> findByProjectCategoryId(Long projectCategoryId);
    @Transactional
    @Modifying
    @Query("delete from ProjectSupply p where p.quotationId = ?1")
    int deleteByQuotationId(Long quotationId);
    @Query("select p from ProjectSupply p where p.quotationId = ?1 order by p.sort ASC")
    @OrderBy(clause = "sort ASC")
    List<ProjectSupply> findByQuotationId(Long quotationId);

    @Query("select p from ProjectSupply p where p.quotationId = ?1 and p.createType = 0 order by p.sort ASC")
    List<ProjectSupply> findByQuotationIdWithCreateTypeZero(Long quotationId);
    @Transactional
    @Modifying
    @Query("update ProjectSupply p set p.deleted = ?1 where p.projectCategoryId = ?2")
    int updateDeletedByProjectCategoryId(Boolean deleted, Long projectCategoryId);
    @Query("select p from ProjectSupply p where p.scheduleId = ?1")
    List<ProjectSupply> findByScheduleId(Long scheduleId);

    @Query("select p from ProjectSupply p where p.projectId = ?1 order by p.sort ASC")
    List<ProjectSupply> findByProjectId(Long projectId);
    @Transactional
    @Modifying
    @Query("delete from ProjectSupply p where p.projectCategoryId = ?1")
    int deleteByProjectCategoryId(Long projectCategoryId);
    @Transactional
    @Modifying
    @Query("delete from ProjectSupply p where p.projectCategoryId in ?1")
    void deleteByProjectCategoryIdIn(Collection<Long> projectCategoryIds);

}
