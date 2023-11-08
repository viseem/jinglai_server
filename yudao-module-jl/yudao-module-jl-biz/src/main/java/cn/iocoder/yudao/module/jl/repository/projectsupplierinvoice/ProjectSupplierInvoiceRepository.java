package cn.iocoder.yudao.module.jl.repository.projectsupplierinvoice;

import cn.iocoder.yudao.module.jl.entity.projectsupplierinvoice.ProjectSupplierInvoice;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
* ProjectSupplierInvoiceRepository
*
*/
public interface ProjectSupplierInvoiceRepository extends JpaRepository<ProjectSupplierInvoice, Long>, JpaSpecificationExecutor<ProjectSupplierInvoice> {
    @Query("select p from ProjectSupplierInvoice p where p.supplierId = ?1")
    List<ProjectSupplierInvoice> findBySupplierId(Long supplierId);

}
