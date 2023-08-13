package cn.iocoder.yudao.module.jl.service.project.listener;

import cn.iocoder.yudao.module.bpm.framework.bpm.core.event.BpmProcessInstanceResultEvent;
import cn.iocoder.yudao.module.bpm.framework.bpm.core.event.BpmProcessInstanceResultEventListener;
import cn.iocoder.yudao.module.jl.controller.admin.contract.vo.ContractApprovalUpdateReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.ProjectApprovalUpdateReqVO;
import cn.iocoder.yudao.module.jl.repository.contract.ContractApprovalRepository;
import cn.iocoder.yudao.module.jl.service.contract.ContractApprovalServiceImpl;
import cn.iocoder.yudao.module.jl.service.project.ProjectApprovalServiceImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * OA 请假单的结果的监听器实现类
 *
 * @author 芋道源码
 */
@Component
public class BpmProjectContractChangeResultListener extends BpmProcessInstanceResultEventListener {

    @Resource
    private ContractApprovalServiceImpl contractApprovalService;

    @Override
    protected String getProcessDefinitionKey() {
        return ContractApprovalServiceImpl.PROCESS_KEY;
    }

    @Override
    protected void onEvent(BpmProcessInstanceResultEvent event) {
        long id = Long.parseLong(event.getBusinessKey());
        String result = event.getResult().toString();
        //获取
        ContractApprovalUpdateReqVO contractApprovalUpdateReqVO = new ContractApprovalUpdateReqVO();
        contractApprovalUpdateReqVO.setId(id);
        contractApprovalUpdateReqVO.setApprovalStage(result);
        contractApprovalService.updateContractApproval(contractApprovalUpdateReqVO);
    }

}
