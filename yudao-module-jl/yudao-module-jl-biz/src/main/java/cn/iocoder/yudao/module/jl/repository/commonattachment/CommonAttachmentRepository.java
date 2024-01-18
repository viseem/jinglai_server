package cn.iocoder.yudao.module.jl.repository.commonattachment;

import cn.iocoder.yudao.module.jl.entity.commonattachment.CommonAttachment;
import org.springframework.data.jpa.repository.*;

/**
* CommonAttachmentRepository
*
*/
public interface CommonAttachmentRepository extends JpaRepository<CommonAttachment, Long>, JpaSpecificationExecutor<CommonAttachment> {

}
