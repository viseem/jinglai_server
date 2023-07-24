package cn.iocoder.yudao.module.jl.repository.animal;

import cn.iocoder.yudao.module.jl.entity.animal.AnimalBox;
import org.springframework.data.jpa.repository.*;

/**
* AnimalBoxRepository
*
*/
public interface AnimalBoxRepository extends JpaRepository<AnimalBox, Long>, JpaSpecificationExecutor<AnimalBox> {

}
