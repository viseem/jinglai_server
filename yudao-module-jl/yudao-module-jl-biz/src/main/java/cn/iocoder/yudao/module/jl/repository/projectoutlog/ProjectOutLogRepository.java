package cn.iocoder.yudao.module.jl.repository.projectoutlog;

import cn.iocoder.yudao.module.jl.entity.projectoutlog.ProjectOutLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* ProjectOutLogRepository
*
*/
public interface ProjectOutLogRepository extends JpaRepository<ProjectOutLog, Long>, JpaSpecificationExecutor<ProjectOutLog> {
    @Transactional
    @Modifying
    @Query("update ProjectOutLog p set p.step1Json = ?1 where p.id = ?2")
    int updateStep1JsonById(String step1Json, Long id);

    @Transactional
    @Modifying
    @Query("update ProjectOutLog p set p.step2Json = ?1 where p.id = ?2")
    int updateStep2JsonById(String step2Json, Long id);

    @Transactional
    @Modifying
    @Query("update ProjectOutLog p set p.step3Json = ?1 where p.id = ?2")
    int updateStep3JsonById(String step3Json, Long id);

    @Transactional
    @Modifying
    @Query("update ProjectOutLog p set p.step4Json = ?1 where p.id = ?2")
    int updateStep4JsonById(String step4Json, Long id);

}
