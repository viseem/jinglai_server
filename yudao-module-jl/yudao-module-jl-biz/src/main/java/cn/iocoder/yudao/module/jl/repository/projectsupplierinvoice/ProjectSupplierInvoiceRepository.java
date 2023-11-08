package cn.iocoder.yudao.module.jl.repository.projectsupplierinvoice;

import cn.iocoder.yudao.module.jl.entity.projectsupplierinvoice.ProjectSupplierInvoice;
import org.springframework.data.jpa.repository.*;

/**
* ProjectSupplierInvoiceRepository
*
*/
public interface ProjectSupplierInvoiceRepository extends JpaRepository<ProjectSupplierInvoice, Long>, JpaSpecificationExecutor<ProjectSupplierInvoice> {

}
