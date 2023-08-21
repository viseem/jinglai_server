package cn.iocoder.yudao.module.jl.jlBpmListener;

import cn.iocoder.yudao.module.bpm.framework.bpm.core.event.BpmProcessInstanceResultEvent;
import cn.iocoder.yudao.module.bpm.framework.bpm.core.event.BpmProcessInstanceResultEventListener;
import cn.iocoder.yudao.module.jl.enums.ProcurementStatusEnums;
import cn.iocoder.yudao.module.jl.repository.animal.AnimalFeedOrderRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProcurementRepository;
import cn.iocoder.yudao.module.jl.service.animal.AnimalFeedOrderServiceImpl;
import cn.iocoder.yudao.module.jl.service.project.ProcurementServiceImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * OA 请假单的结果的监听器实现类
 *
 * @author 芋道源码
 */
@Component
public class BpmProjectProcurementAuditResultListener extends BpmProcessInstanceResultEventListener {


    @Resource
    private ProcurementRepository procurementRepository;

    @Override
    protected String getProcessDefinitionKey() {
        return ProcurementServiceImpl.PROCESS_KEY;
    }

    @Override
    protected void onEvent(BpmProcessInstanceResultEvent event) {
        long id = Long.parseLong(event.getBusinessKey());
        String result = event.getResult().toString();

        //TODO 获取任务的回复

        //获取
        procurementRepository.updateStatusById(id,ProcurementStatusEnums.WAITING_FINANCE_CONFIRM.getStatus());
    }

}
