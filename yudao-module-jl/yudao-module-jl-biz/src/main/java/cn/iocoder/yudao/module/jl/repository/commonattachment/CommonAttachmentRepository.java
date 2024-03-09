package cn.iocoder.yudao.module.jl.repository.commonattachment;

import cn.iocoder.yudao.module.jl.entity.commonattachment.CommonAttachment;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* CommonAttachmentRepository
*
*/
public interface CommonAttachmentRepository extends JpaRepository<CommonAttachment, Long>, JpaSpecificationExecutor<CommonAttachment> {
    @Query("select c from CommonAttachment c where c.type = ?1 and c.refId = ?2")
    List<CommonAttachment> findByTypeAndRefId(String type, Long refId);
    @Transactional
    @Modifying
    @Query("delete from CommonAttachment c where c.refId = ?1 and c.type = ?2")
    int deleteByRefIdAndType(Long refId, String type);

}
