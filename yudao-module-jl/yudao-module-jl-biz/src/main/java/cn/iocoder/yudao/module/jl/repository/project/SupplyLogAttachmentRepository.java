package cn.iocoder.yudao.module.jl.repository.project;

import cn.iocoder.yudao.module.jl.entity.project.SupplyLogAttachment;
import org.springframework.data.jpa.repository.*;

/**
* SupplyLogAttachmentRepository
*
*/
public interface SupplyLogAttachmentRepository extends JpaRepository<SupplyLogAttachment, Long>, JpaSpecificationExecutor<SupplyLogAttachment> {

}
