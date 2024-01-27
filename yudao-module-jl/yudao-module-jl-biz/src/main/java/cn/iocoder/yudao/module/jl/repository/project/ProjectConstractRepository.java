package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.ProjectConstract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

/**
* ProjectConstractRepository
*
*/
public interface ProjectConstractRepository extends JpaRepository<ProjectConstract, Long>, JpaSpecificationExecutor<ProjectConstract> {
    @Query("select p from ProjectConstract p where p.creator in ?1 and p.status = ?2")
    List<ProjectConstract> getProjectContract(Collection<Long> creators, String status);
    @Query("select p from ProjectConstract p where p.projectId = ?1 and p.status = ?2")
    List<ProjectConstract> findByProjectIdAndStatus(Long projectId, String status);
    @Transactional
    @Modifying
    @Query("update ProjectConstract p set p.invoicedPrice = ?1 where p.id = ?2")
    int updateInvoicedPriceById(BigDecimal invoicedPrice, Long id);
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
    int updateReceivedPriceById(BigDecimal receivedPrice, Long id);
    @Query("select p from ProjectConstract p where p.customerId = ?1 and p.status = ?2")
    List<ProjectConstract> findByCustomerIdAndStatus(Long customerId, String status);

    ProjectConstract findFirstByOrderByIdDesc();
}
