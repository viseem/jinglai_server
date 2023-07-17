package cn.iocoder.yudao.module.jl.repository.animal;

import cn.iocoder.yudao.module.jl.entity.animal.AnimalRoom;
import org.springframework.data.jpa.repository.*;

/**
* AnimalRoomRepository
*
*/
public interface AnimalRoomRepository extends JpaRepository<AnimalRoom, Long>, JpaSpecificationExecutor<AnimalRoom> {

}
