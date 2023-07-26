package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectFund;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* ProjectFundRepository
*
*/
public interface ProjectFundRepository extends JpaRepository<ProjectFund, Long>, JpaSpecificationExecutor<ProjectFund> {
    @Transactional
    @Modifying
    @Query("update ProjectFund p set p.receivedPrice = ?1 where p.id = ?2")
    int updateReceivedPriceById(Integer receivedPrice, Long id);

}
