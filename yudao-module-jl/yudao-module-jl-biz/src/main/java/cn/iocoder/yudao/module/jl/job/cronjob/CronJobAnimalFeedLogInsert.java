package cn.iocoder.yudao.module.jl.job.cronjob;

import cn.iocoder.yudao.framework.quartz.core.handler.JobHandler;
import cn.iocoder.yudao.framework.tenant.core.job.TenantJob;
import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedLog;
import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedOrder;
import cn.iocoder.yudao.module.jl.enums.AnimalFeedStageEnums;
import cn.iocoder.yudao.module.jl.repository.animal.AnimalFeedLogRepository;
import cn.iocoder.yudao.module.jl.repository.animal.AnimalFeedOrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 支付通知 Job
 * 通过不断扫描待通知的 PayNotifyTaskDO 记录，回调业务线的回调接口
 *
 * @author 芋道源码
 */
@Component
@TenantJob // 多租户
@Slf4j
public class CronJobAnimalFeedLogInsert implements JobHandler {

    @Resource
    private AnimalFeedOrderRepository animalFeedOrderRepository;
    @Resource
    private AnimalFeedLogRepository animalFeedLogRepository;

    @Override
    public String execute(String param) throws Exception {
        AtomicReference<String> logs = new AtomicReference<>("");

        List<AnimalFeedOrder> feedingOrders = animalFeedOrderRepository.findByStage(AnimalFeedStageEnums.FEEDING.getStatus());

        for (AnimalFeedOrder animalFeedOrder : feedingOrders) {
            Optional<AnimalFeedLog> lastFeedLogOptional = animalFeedLogRepository.findFirstByFeedOrderIdOrderByIdDesc(animalFeedOrder.getId());

            AnimalFeedLog newLog = new AnimalFeedLog();
            newLog.setFeedOrderId(animalFeedOrder.getId());
            newLog.setType("AUTO_LOG");
            newLog.setMark("自动记录");
            newLog.setChangeQuantity(0);
            newLog.setChangeCageQuantity(0);
            newLog.setCreator(0L);

            if (lastFeedLogOptional.isPresent()) {
                AnimalFeedLog lastFeedLog = lastFeedLogOptional.get();
                LocalDate today = LocalDate.now();

                if (!lastFeedLog.getCreateTime().toLocalDate().equals(today)) {
                    logs.updateAndGet(v -> v + lastFeedLog);
                    newLog.setQuantity(lastFeedLog.getQuantity());
                    newLog.setCageQuantity(lastFeedLog.getCageQuantity());
                    newLog.setStores(lastFeedLog.getStores());
                }
            } else {
                newLog.setQuantity(animalFeedOrder.getQuantity());
                newLog.setCageQuantity(animalFeedOrder.getCageQuantity());
                logs.updateAndGet(v -> v + newLog);
            }

            animalFeedLogRepository.save(newLog);
        }

        return String.format("新增饲养日志 %s", logs.get());
    }

}
