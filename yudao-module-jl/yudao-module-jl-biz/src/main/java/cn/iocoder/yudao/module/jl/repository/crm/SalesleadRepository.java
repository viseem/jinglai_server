package cn.iocoder.yudao.module.jl.repository.crm;

import cn.iocoder.yudao.module.jl.entity.crm.Saleslead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;

/**
* SalesleadRepository
*
*/
public interface SalesleadRepository extends JpaRepository<Saleslead, Long>, JpaSpecificationExecutor<Saleslead> {
    @Query("select count(s) from Saleslead s where s.id in ?1 and s.status = ?2")
    Integer countByIdInAndStatus(Long[] ids, Integer status);
    @Query("select count(s) from Saleslead s where s.id in ?1")
    Integer countByIdIn(Long[] ids);

    @Query("select count(s) from Saleslead s " +
            "where s.quotationCreateTime between ?1 and ?2 and s.managerId in ?3 and s.status = ?4")
    Integer countByQuotationCreateTimeBetweenAndManagerIdInAndStatus(LocalDateTime quotationCreateTimeStart, LocalDateTime quotationCreateTimeEnd, Long[] managerIds, Integer status);
    @Query("select count(s) from Saleslead s where s.quotationCreateTime between ?1 and ?2 and s.managerId in ?3")
    Integer countByQuotationCreateTimeBetweenAndManagerIdIn(LocalDateTime quotationCreateTimeStart, LocalDateTime quotationCreateTimeEnd, Long[] managerIds);

    @Transactional
    @Modifying
    @Query("update Saleslead s set s.creator = ?1 where s.customerId = ?2")
    int updateCreatorByCustomerId(Long creator, Long customerId);

    @Transactional
    @Modifying
    @Query("update Saleslead s set s.contractId = ?1 where s.id = ?2")
    int updateContractIdById(Long contractId, Long id);
    @Transactional
    @Modifying
    @Query("update Saleslead s set s.quotationUpdateTime = ?1 where s.id = ?2")
    int updateQuotationUpdateTimeById(LocalDateTime quotationUpdateTime, Long id);

    @Transactional
    @Modifying
    @Query("update Saleslead s set s.quotationProcessId = ?1, s.quotationAuditMark = ?2, s.quotationAuditStatus = ?3 " +
            "where s.id = ?4")
    int updateQuotationProcessIdAndQuotationAuditMarkAndQuotationAuditStatusById(String quotationProcessId, String quotationAuditMark, String quotationAuditStatus, Long id);

//    @Transactional
//    @Modifying
//    @Query(value = "UPDATE jl_crm_saleslead SET quotation_process_id = :quotationProcessId, quotation_audit_mark = :quotationAuditMark, quotation_audit_status = :quotationAuditStatus WHERE id = :id", nativeQuery = true)
//    int updateQuotationProcessIdAndQuotationAuditMarkAndQuotationAuditStatusById(String quotationProcessId,String quotationAuditMark,String quotationAuditStatus,Long id);

    @Transactional
    @Modifying
    @Query("update Saleslead s set s.currentQuotationId = ?1 where s.id = ?2")
    int updateCurrentQuotationIdById(Long currentQuotationId, Long id);
    @Transactional
    @Modifying
    @Query("update Saleslead s set s.quotationProcessId = ?1, s.quotationAuditStatus = ?2 where s.id = ?3")
    int updateQuotationProcessIdAndQuotationAuditStatusById(String quotationProcessId, String quotationAuditStatus, Long id);

    @Transactional
    @Modifying
    @Query("update Saleslead s set s.quotation = ?1, s.lastFollowTime = ?2, s.quotationUpdateTime = ?3 " +
            "where s.id = ?4")
    int updateQuotationAndLastFollowTimeAndQuotationUpdateTimeById(BigDecimal quotation, LocalDateTime lastFollowTime, LocalDateTime quotationUpdateTime, Long id);

    @Transactional
    @Modifying
    @Query("update Saleslead s set s.lastFollowTime = ?1 where s.id = ?2")
    int updateLastFollowTimeById(LocalDateTime lastFollowTime, Long id);
    @Query("select count(s) from Saleslead s where s.createTime between ?1 and ?2 and s.creator in ?3 and s.status = ?4")
    Integer countByCreateTimeBetweenAndCreatorInAndStatus(LocalDateTime createTimeStart, LocalDateTime createTimeEnd, Long[] creators, Integer status);
    @Query("select count(s) from Saleslead s where s.createTime between ?1 and ?2 and s.creator in ?3")
    Integer countByCreateTimeBetweenAndCreatorIn(LocalDateTime createTimeStart, LocalDateTime createTimeEnd, Long[] creators);
    @Transactional
    @Modifying
    @Query("update Saleslead s set s.jsonLog = ?1 where s.id = ?2")
    int updateJsonLogById(String jsonLog, Long id);
    @Transactional
    @Modifying
    @Query("update Saleslead s set s.creator = ?1 where s.id = ?2")
    int updateCreatorById(Long creator, Long id);

    @Transactional
    @Modifying
    @Query("update Saleslead s set s.quotationMark = ?1,s.quotationJsonFile = ?2 where s.id = ?3")
    int updateQuotationMarkAndQuotationJsonFileById(String quotationMark, String jsonFile,Long id);
    @Transactional
    @Modifying
    @Query("update Saleslead s set s.managerId = ?1,s.assignMark=?2 where s.id = ?3")
    int updateManagerIdAndAssignMarkById(Long managerId,String assignMark, Long id);
    @Transactional
    @Modifying
    @Query("update Saleslead s set s.status = ?1 where s.projectId = ?2")
    int updateStatusByProjectId(Integer status, Long projectId);
    @Query("select count(s) from Saleslead s where s.managerId = ?1 and s.status = ?2")
    Integer countByManagerIdAndStatus(Long managerId, Integer status);
    @Query("select count(s) from Saleslead s where s.creator = ?1 and (s.status != ?2 or s.status is null)")
    Integer countByCreatorAndStatusNot(Long creator, Integer status);
    @Query("select count(s) from Saleslead s where s.status != ?1")
    Integer countByStatusNot(Integer status);
    @Transactional
    @Modifying
    @Query("update Saleslead s set s.lastFollowUpId = ?2 where s.id = ?1")
    void updateLastFollowUpIdById( Long id, @NonNull Long lastFollowUpId);

    @Transactional
    @Modifying
    @Query("update Saleslead s set s.quotation = ?2 where s.id = ?1")
    void updateQuotationById(Long id, Long quotationId);


    @Transactional
    @Modifying
    @Query("update Saleslead s set s.quotation = ?2 where s.projectId = ?1")
    void updateQuotationByProjectId(Long projectId, BigDecimal quotation);
}
