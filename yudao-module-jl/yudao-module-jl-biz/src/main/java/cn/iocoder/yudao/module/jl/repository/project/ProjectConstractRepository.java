package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectConstract;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* ProjectConstractRepository
*
*/
public interface ProjectConstractRepository extends JpaRepository<ProjectConstract, Long>, JpaSpecificationExecutor<ProjectConstract> {
    @Query("select p from ProjectConstract p where p.sn = ?1")
    ProjectConstract findBySn(String sn);
    @Transactional
    @Modifying
    @Query("update ProjectConstract p set p.projectDocumentId = ?1 where p.id = ?2")
    int updateProjectDocumentIdById(Long projectDocumentId, Long id);
    @Transactional
    @Modifying
    @Query("update ProjectConstract p set p.payStatus = ?1 where p.id = ?2")
    int updatePayStatusById(String payStatus, Long id);
    @Transactional
    @Modifying
    @Query("update ProjectConstract p set p.receivedPrice = ?1 where p.id = ?2")
    int updateReceivedPriceById(Integer receivedPrice, Long id);
    @Query("select p from ProjectConstract p where p.customerId = ?1 and p.status = ?2")
    List<ProjectConstract> findByCustomerIdAndStatus(Long customerId, String status);

    ProjectConstract findFirstByOrderByIdDesc();
}
