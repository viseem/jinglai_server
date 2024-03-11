package cn.iocoder.yudao.module.jl.repository.picollaboration;

import cn.iocoder.yudao.module.jl.entity.picollaboration.PiCollaboration;
import org.springframework.data.jpa.repository.*;

/**
* PiCollaborationRepository
*
*/
public interface PiCollaborationRepository extends JpaRepository<PiCollaboration, Long>, JpaSpecificationExecutor<PiCollaboration> {

}
