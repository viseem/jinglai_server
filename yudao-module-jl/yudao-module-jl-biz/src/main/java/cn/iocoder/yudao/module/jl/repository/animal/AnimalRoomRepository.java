package cn.iocoder.yudao.module.jl.repository.animal;

import cn.iocoder.yudao.module.jl.entity.animal.AnimalRoom;
import org.springframework.data.jpa.repository.*;
import org.springframework.transaction.annotation.Transactional;

/**
* AnimalRoomRepository
*
*/
public interface AnimalRoomRepository extends JpaRepository<AnimalRoom, Long>, JpaSpecificationExecutor<AnimalRoom> {
    @Query("select a from AnimalRoom a where a.code = ?1")
    AnimalRoom findByCode(String code);
    @Transactional
    @Modifying
    @Query("update AnimalRoom a set a.code = ?1 where a.id = ?2")
    int updateCodeById(String code, Long id);

}
