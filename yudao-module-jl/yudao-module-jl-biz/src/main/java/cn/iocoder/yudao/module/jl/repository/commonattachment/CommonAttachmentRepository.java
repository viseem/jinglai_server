package cn.iocoder.yudao.module.jl.repository.commonattachment;

import cn.iocoder.yudao.module.jl.entity.commonattachment.CommonAttachment;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* CommonAttachmentRepository
*
*/
public interface CommonAttachmentRepository extends JpaRepository<CommonAttachment, Long>, JpaSpecificationExecutor<CommonAttachment> {
    @Transactional
    @Modifying
    @Query("delete from CommonAttachment c where c.refId = ?1 and c.type = ?2")
    int deleteByRefIdAndType(Long refId, String type);

}
