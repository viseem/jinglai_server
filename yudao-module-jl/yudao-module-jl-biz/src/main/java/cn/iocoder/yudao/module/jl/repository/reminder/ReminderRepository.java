package cn.iocoder.yudao.module.jl.repository.reminder;

import cn.iocoder.yudao.module.jl.entity.reminder.Reminder;
import org.springframework.data.jpa.repository.*;

/**
* ReminderRepository
*
*/
public interface ReminderRepository extends JpaRepository<Reminder, Long>, JpaSpecificationExecutor<Reminder> {

}
