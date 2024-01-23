package cn.iocoder.yudao.module.jl.repository.collaborationrecord;

import cn.iocoder.yudao.module.jl.entity.collaborationrecord.CollaborationRecord;
import org.springframework.data.jpa.repository.*;

/**
* CollaborationRecordRepository
*
*/
public interface CollaborationRecordRepository extends JpaRepository<CollaborationRecord, Long>, JpaSpecificationExecutor<CollaborationRecord> {

}
