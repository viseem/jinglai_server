package cn.iocoder.yudao.module.jl.jlBpmListener;

import cn.iocoder.yudao.module.bpm.framework.bpm.core.event.BpmProcessInstanceResultEvent;
import cn.iocoder.yudao.module.bpm.framework.bpm.core.event.BpmProcessInstanceResultEventListener;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.ProjectApprovalUpdateReqVO;
import cn.iocoder.yudao.module.jl.repository.animal.AnimalFeedOrderRepository;
import cn.iocoder.yudao.module.jl.service.animal.AnimalFeedOrderServiceImpl;
import cn.iocoder.yudao.module.jl.service.project.ProjectApprovalServiceImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * OA 请假单的结果的监听器实现类
 *
 * @author 芋道源码
 */
@Component
public class BpmFeedOrderAuditResultListener extends BpmProcessInstanceResultEventListener {

    @Resource
    private AnimalFeedOrderServiceImpl animalFeedOrderService;

    @Resource
    private AnimalFeedOrderRepository animalFeedOrderRepository;

    @Override
    protected String getProcessDefinitionKey() {
        return AnimalFeedOrderServiceImpl.PROCESS_KEY;
    }

    @Override
    protected void onEvent(BpmProcessInstanceResultEvent event) {
        long id = Long.parseLong(event.getBusinessKey());
        String result = event.getResult().toString();

        //TODO 获取任务的回复

        //获取
        animalFeedOrderRepository.updateStageById(result,id);
    }

}
