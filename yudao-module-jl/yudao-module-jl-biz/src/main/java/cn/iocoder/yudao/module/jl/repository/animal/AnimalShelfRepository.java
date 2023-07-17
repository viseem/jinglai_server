package cn.iocoder.yudao.module.jl.repository.animal;

import cn.iocoder.yudao.module.jl.entity.animal.AnimalShelf;
import org.springframework.data.jpa.repository.*;

/**
* AnimalShelfRepository
*
*/
public interface AnimalShelfRepository extends JpaRepository<AnimalShelf, Long>, JpaSpecificationExecutor<AnimalShelf> {

}
