package cn.iocoder.yudao.module.jl.repository.laboratory;

import cn.iocoder.yudao.module.jl.entity.laboratory.LaboratoryLab;
import org.springframework.data.jpa.repository.*;

import java.util.Collection;
import java.util.List;

/**
* LaboratoryLabRepository
*
*/
public interface LaboratoryLabRepository extends JpaRepository<LaboratoryLab, Long>, JpaSpecificationExecutor<LaboratoryLab> {
    @Query("select l from LaboratoryLab l where l.id in ?1")
    List<LaboratoryLab> findByIdIn(Collection<Long> ids);

}
