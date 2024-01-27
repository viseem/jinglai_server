package cn.iocoder.yudao.module.jl.repository.crm;

import cn.iocoder.yudao.module.jl.entity.crm.Followup;
import org.springframework.data.jpa.repository.*;

import java.time.LocalDateTime;
import java.util.Collection;

/**
* FollowupRepository
*
*/
public interface FollowupRepository extends JpaRepository<Followup, Long>, JpaSpecificationExecutor<Followup> {
    @Query("select count(f) from Followup f where f.createTime between ?1 and ?2 and f.creator in ?3")
    Integer countByCreateTimeBetweenAndCreatorIn(LocalDateTime createTimeStart, LocalDateTime createTimeEnd, Long[] creators);

}
