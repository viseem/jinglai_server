package cn.iocoder.yudao.module.jl.repository.invoiceapplication;

import cn.iocoder.yudao.module.jl.entity.invoiceapplication.InvoiceApplication;
import org.springframework.data.jpa.repository.*;

/**
* InvoiceApplicationRepository
*
*/
public interface InvoiceApplicationRepository extends JpaRepository<InvoiceApplication, Long>, JpaSpecificationExecutor<InvoiceApplication> {

}
