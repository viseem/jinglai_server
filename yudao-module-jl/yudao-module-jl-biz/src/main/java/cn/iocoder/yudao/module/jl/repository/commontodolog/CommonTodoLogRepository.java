package cn.iocoder.yudao.module.jl.repository.commontodolog;

import cn.iocoder.yudao.module.jl.entity.commontodolog.CommonTodoLog;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* CommonTodoLogRepository
*
*/
public interface CommonTodoLogRepository extends JpaRepository<CommonTodoLog, Long>, JpaSpecificationExecutor<CommonTodoLog> {
    @Transactional
    @Modifying
    @Query("update CommonTodoLog c set c.status = ?1 where c.id = ?2")
    int updateStatusById(String status, Long id);
    @Transactional
    @Modifying
    @Query("update CommonTodoLog c set c.status = ?1 where c.refId = ?2 and c.type = ?3")
    int updateStatusByRefIdAndType(String status, Long refId, String type);
    @Query("select c from CommonTodoLog c where c.type = ?1 and c.refId = ?2")
    CommonTodoLog findByTypeAndRefId(String type, Long refId);

}
