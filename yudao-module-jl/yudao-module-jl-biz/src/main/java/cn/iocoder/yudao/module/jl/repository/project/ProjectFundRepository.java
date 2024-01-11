package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectFund;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
* ProjectFundRepository
*
*/
public interface ProjectFundRepository extends JpaRepository<ProjectFund, Long>, JpaSpecificationExecutor<ProjectFund> {
    @Query("select p from ProjectFund p where p.actualPaymentTime between ?1 and ?2")
    List<ProjectFund> findByActualPaymentTimeBetween(LocalDateTime actualPaymentTimeStart, LocalDateTime actualPaymentTimeEnd);
    @Transactional
    @Modifying
    @Query("update ProjectFund p set p.actualPaymentTime = ?1 where p.id = ?2")
    int updateActualPaymentTimeById(LocalDateTime actualPaymentTime, Long id);
    @Transactional
    @Modifying
    @Query("update ProjectFund p set p.status = ?1 , p.payMark = ?2 where p.id = ?3")
    int updateStatusAndPayMarkById(String status,String mark, Long id);
    @Query("select count(p) from ProjectFund p where p.receivedPrice < p.price")
    Integer countByNotReceivedComplete();
    @Query("select count(p) from ProjectFund p where p.creator = ?1 and p.receivedPrice < p.price")
    Integer countByNotReceivedCompleteAndCreator(Long creator);
    @Query("select count(p) from ProjectFund p where p.creator = ?1 and (p.status <> ?2 or p.status is null)")
    Integer countByCreatorAndStatusNot(Long creator, String status);

    @Query("select count(p) from ProjectFund p where p.status <> ?1 or p.status is null")
    Integer countByStatusNot(String status);
    @Query("select p from ProjectFund p where p.customerId = ?1")
    List<ProjectFund> findByCustomerId(Long customerId);
    @Transactional
    @Modifying
    @Query("update ProjectFund p set p.receivedPrice = ?1 where p.id = ?2")
    int updateReceivedPriceById(BigDecimal receivedPrice, Long id);

}
