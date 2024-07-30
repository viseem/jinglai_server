package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * ProjectRepository
 */
public interface ProjectOnlyRepository extends JpaRepository<ProjectOnly, Long>, JpaSpecificationExecutor<ProjectOnly> {
    @Transactional
    @Modifying
    @Query("update ProjectOnly p set p.processInstanceId = ?1, p.outboundApplyResult = ?2 , p.outboundUserId = null, p.outboundAuditMark = null " +
            "where p.id = ?3")
    int initOutAuditStatus(String processInstanceId,String outboundApplyResult,  Long id);
    @Transactional
    @Modifying
    @Query("update ProjectOnly p set p.salesId = ?1 where p.customerId = ?2")
    int updateSalesIdByCustomerId(Long salesId, Long customerId);

    @Transactional
    @Modifying
    @Query("update ProjectOnly p set p.stage = ?1 where p.id = ?2")
    int updateStageById(String stage, Long id);
    @Transactional
    @Modifying
    @Query("update ProjectOnly p set p.doInstanceId = ?1, p.doUserId = ?2, p.doApplyResult = ?3,p.doAuditMark = ?4 where p.id = ?5")
    int updateDoInstanceIdAndDoUserIdAndDoApplyResultAndAuditMarkById(String doInstanceId, Long doUserId, String doApplyResult,String doAuditMark, Long id);
    @Transactional
    @Modifying
    @Query("update ProjectOnly p set p.doInstanceId = ?1, p.doUserId = ?2 where p.id = ?3")
    int updateDoInstanceIdAndDoUserIdById(String doInstanceId, Long doUserId, Long id);
    @Transactional
    @Modifying
    @Query("update ProjectOnly p set p.processInstanceId = ?1, p.outboundApplyResult = ?2 where p.id = ?3")
    int updateProcessInstanceIdAndOutboundApplyResultById(String processInstanceId, String outboundApplyResult, Long id);

    @Transactional
    @Modifying
    @Query("update ProjectOnly p set  p.outboundApplyResult = ?1 where p.id = ?2")
    int updateOutboundApplyResultById(String outboundApplyResult, Long id);

    @Query("select p from ProjectOnly p where p.salesId in ?1 and p.code is not null and p.managerId not in ?2")
    List<ProjectOnly> findBySalesIdInAndCodeNotNullAndManagerIdNotIn(List<Long> salesIds, List<Long> managerIds);

    @Query("select p from ProjectOnly p where p.managerId in ?1 and p.code is not null")
    List<ProjectOnly> findByInManagerIdAndCodeNotNull(Long[] managerIds);

    @Transactional
    @Modifying
    @Query("update ProjectOnly p set p.customerSignImgUrl = ?1, p.customerSignMark = ?2, p.customerSignTime = ?3 " +
            "where p.id = ?4")
    int updateCustomerSignImgUrlAndCustomerSignMarkAndCustomerSignTimeById(String customerSignImgUrl, String customerSignMark, LocalDateTime customerSignTime, Long id);

}
