package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectFund;
import cn.iocoder.yudao.module.jl.entity.project.ProjectFundOnly;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
* ProjectFundRepository
*
*/
public interface ProjectFundOnlyRepository extends JpaRepository<ProjectFundOnly, Long>, JpaSpecificationExecutor<ProjectFundOnly> {
    @Query("select p from ProjectFundOnly p where p.actualPaymentTime between ?1 and ?2")
    List<ProjectFundOnly> findByActualPaymentTimeBetween(LocalDateTime actualPaymentTimeStart, LocalDateTime actualPaymentTimeEnd);


}
