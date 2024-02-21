package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectConstractOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
* ProjectConstractRepository
*
*/
public interface ProjectConstractOnlyRepository extends JpaRepository<ProjectConstractOnly, Long>, JpaSpecificationExecutor<ProjectConstractOnly> {
    @Query("select p from ProjectConstractOnly p " +
            "where p.creator in ?1 and p.signedTime between ?2 and ?3 and p.status = ?4")
    List<ProjectConstractOnly> findByCreatorInAndSignedTimeBetweenAndStatus(Long[] creators, LocalDateTime createTimeStart, LocalDateTime createTimeEnd, String status);

//     and p.createTime > ?3 and p.createTime < ?4
    @Query("select p from ProjectConstractOnly p " +
            "where p.creator in ?1 and p.status = ?2 and p.signedTime between ?3 and ?4")
    List<ProjectConstractOnly> getContractFinancialStatistic(Collection<Long> creators, String status, LocalDateTime createTime, LocalDateTime createTime1);
}
