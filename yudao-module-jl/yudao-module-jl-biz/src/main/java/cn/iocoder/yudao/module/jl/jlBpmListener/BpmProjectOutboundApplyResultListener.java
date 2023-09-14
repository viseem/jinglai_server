package cn.iocoder.yudao.module.jl.jlBpmListener;

import cn.iocoder.yudao.module.bpm.enums.task.BpmProcessInstanceResultEnum;
import cn.iocoder.yudao.module.bpm.framework.bpm.core.event.BpmProcessInstanceResultEvent;
import cn.iocoder.yudao.module.bpm.framework.bpm.core.event.BpmProcessInstanceResultEventListener;
import cn.iocoder.yudao.module.jl.enums.ProjectStageEnums;
import cn.iocoder.yudao.module.jl.repository.financepayment.FinancePaymentRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectRepository;
import cn.iocoder.yudao.module.jl.service.financepayment.FinancePaymentServiceImpl;
import cn.iocoder.yudao.module.jl.service.project.ProjectServiceImpl;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * OA 请假单的结果的监听器实现类
 *
 * @author 芋道源码
 */
@Component
public class BpmProjectOutboundApplyResultListener extends BpmProcessInstanceResultEventListener {

    @Resource
    private ProjectRepository projectRepository;

    @Override
    protected String getProcessDefinitionKey() {
        return ProjectServiceImpl.PROCESS_KEY;
    }

    @Override
    @Transactional
    protected void onEvent(BpmProcessInstanceResultEvent event) {
        long id = Long.parseLong(event.getBusinessKey());
        String result = event.getResult().toString();

        //如果是审核通过
        if (String.valueOf(BpmProcessInstanceResultEnum.APPROVE.getResult()).equals(result)){
            projectRepository.updateStageById(ProjectStageEnums.OUTED.getStatus(), id);
        }
        projectRepository.updateOutboundApplyResultById(result, id);
        //获取
    }

}
