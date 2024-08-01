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
    @Query("update ProjectOutLog p set p.customerScoreJson = ?1, p.customerSignImgUrl = ?2, p.customerComment = ?3 " +
            "where p.id = ?4")
    int updateCustomerScoreJsonAndCustomerSignImgUrlAndCustomerCommentById(String customerScoreJson, String customerSignImgUrl, String customerComment, Long id);
    @Transactional
    @Modifying
    @Query("update ProjectOutLog p set p.customerScoreJson = ?1 where p.id = ?2")
    int updateCustomerScoreJsonById(String customerScoreJson, Long id);
    @Transactional
    @Modifying
    @Query("update ProjectOutLog p set p.dataSignJson = ?1 where p.id = ?2")
    int updateDataSignJsonById(String dataSignJson, Long id);

}
