package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.Project;
import cn.iocoder.yudao.module.jl.mapper.project.ProjectMapper;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
* ProjectRepository
*
*/
public interface ProjectRepository extends JpaRepository<Project, Long>, JpaSpecificationExecutor<Project> {
    @Query("select p from Project p where p.code like concat(?1, '%')")
    Project findByCodeStartsWith(String code);
    @Query("select p from Project p where p.code = ?1")
    Project findByCode(String code);
    @Transactional
    @Modifying
    @Query("update Project p set p.currentQuotationId = ?1 where p.id = ?2")
    int updateCurrentQuotationIdById(Long currentQuotationId, Long id);
    @Query("select p from Project p where p.code is not null")
    List<Project> findByCodeNotNull();
    @Transactional
    @Modifying
    @Query("update Project p set p.outboundApplyResult = ?1 where p.id = ?2")
    int updateOutboundApplyResultById(String outboundApplyResult, Long id);
    @Transactional
    @Modifying
    @Query("update Project p set p.stage = ?1 where p.id = ?2")
    int updateStageById(String stage, Long id);
    @Transactional
    @Modifying
    @Query("update Project p set p.stage = ?2,p.processInstanceId=?3,p.outboundUserId=?4 where p.id = ?1")
    int updateStageAndProcessInstanceIdAndApplyUserById(Long id,String stage,String processInstanceId,Long applyUserId);
    @Query("select count(p) from Project p where p.managerId = ?1 and (p.stage <> ?2 or p.stage is null) and p.code is not null")
    Integer countByManagerIdAndStageNot(Long managerId, String stage);

    @Query("select count(p) from Project p where p.salesId = ?1 and (p.stage <> ?2 or p.stage is null) and p.code is not null")
    Integer countBySalesIdAndStageNot(Long creator, String stage);

    @Query("select count(p) from Project p where p.stage = ?1  and p.code is not null")
    Integer countByStage(String stage);
    @Query("select count(p) from Project p where p.stage = ?1 and p.managerId = ?2 and p.code is not null")
    Integer countByStageAndManagerId(String stage, Long managerId);

    @Query("select count(p) from Project p where p.stage = ?1 and p.salesId = ?2 and p.code is not null")
    Integer countByStageAndSalesId(String stage, Long salesId);

    @Transactional
    @Modifying
    @Query("update Project p set p.currentScheduleId = ?2 where p.id = ?1")
    int updateCurrentScheduleIdById(Long projectId, Long scheduleId);


    Project findFirstByOrderByIdDesc();


}
