package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.AppProject;
import cn.iocoder.yudao.module.jl.entity.project.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

/**
* ProjectRepository
*
*/
public interface AppProjectRepository extends JpaRepository<AppProject, Long>, JpaSpecificationExecutor<AppProject> {
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
