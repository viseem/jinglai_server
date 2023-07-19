package cn.iocoder.yudao.module.jl.repository.animal;

import cn.iocoder.yudao.module.jl.entity.animal.AnimalShelf;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* AnimalShelfRepository
*
*/
public interface AnimalShelfRepository extends JpaRepository<AnimalShelf, Long>, JpaSpecificationExecutor<AnimalShelf> {
    @Transactional
    @Modifying
    @Query("update AnimalShelf a set a.code = ?1 where a.id = ?2")
    int updateCodeById(String code, Long id);
    @Query("select a from AnimalShelf a where a.code = ?1")
    AnimalShelf findByCode(String code);

}
