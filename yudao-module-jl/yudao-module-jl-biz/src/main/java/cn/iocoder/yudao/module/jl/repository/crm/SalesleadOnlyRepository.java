package cn.iocoder.yudao.module.jl.repository.crm;

import cn.iocoder.yudao.module.jl.entity.crm.SalesleadOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
* SalesleadRepository
*
*/
public interface SalesleadOnlyRepository extends JpaRepository<SalesleadOnly, Long>, JpaSpecificationExecutor<SalesleadOnly> {
    @Query("select count(s) from SalesleadOnly s where s.managerId = ?1 and s.quotation > ?2")
    int countByManagerIdAndQuotationGreaterThan(Long managerId, Long quotation);

    @Query("select count(s) from SalesleadOnly s where s.managerId = ?1 and s.quotation > ?2 and s.updateTime between ?3 and ?4")
    int countByManagerIdAndQuotationGreaterThanAndUpdateTimeBetween(Long managerId, BigDecimal quotation,LocalDateTime start, LocalDateTime end);

    @Transactional
    @Modifying
    @Query("update Saleslead s set s.lastFollowTime = ?1 where s.projectId = ?2")
    int updateLastFollowTimeByProjectId(LocalDateTime lastFollowTime, Long projectId);
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
    @Query("update Saleslead s set s.quotationMark = ?1 where s.id = ?2")
    int updateQuotationMarkById(String quotationMark, Long id);
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

    @Query("select count(s) from Saleslead s where s.managerId = ?1 and s.status = ?2 and s.updateTime between ?3 and ?4")
    Integer countByManagerIdAndStatusAndUpdateTimeBetween(Long managerId, Integer status,LocalDateTime start, LocalDateTime end);
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
    @Query("update Saleslead s set s.quotation = ?2,s.status = ?3 where s.projectId = ?1")
    void updateQuotationAndStatusByProjectId(Long projectId,Long quotation, Integer status);

    @Transactional
    @Modifying
    @Query("update Saleslead s set s.quotation = ?2 where s.projectId = ?1")
    void updateQuotationByProjectId(Long projectId, BigDecimal quotation);
}
