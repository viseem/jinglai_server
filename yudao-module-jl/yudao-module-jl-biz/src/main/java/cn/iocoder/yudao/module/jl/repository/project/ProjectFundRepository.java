package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectFund;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* ProjectFundRepository
*
*/
public interface ProjectFundRepository extends JpaRepository<ProjectFund, Long>, JpaSpecificationExecutor<ProjectFund> {
    @Query("select count(p) from ProjectFund p where p.creator = ?1 and p.status <> ?2")
    Integer countByCreatorAndStatusNot(Long creator, String status);
    @Query("select p from ProjectFund p where p.customerId = ?1")
    List<ProjectFund> findByCustomerId(Long customerId);
    @Transactional
    @Modifying
    @Query("update ProjectFund p set p.receivedPrice = ?1 where p.id = ?2")
    int updateReceivedPriceById(Integer receivedPrice, Long id);

}
