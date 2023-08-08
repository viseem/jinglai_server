package cn.iocoder.yudao.module.jl.service.project.listener;

import cn.iocoder.yudao.module.bpm.framework.bpm.core.event.BpmProcessInstanceResultEvent;
import cn.iocoder.yudao.module.bpm.framework.bpm.core.event.BpmProcessInstanceResultEventListener;
import cn.iocoder.yudao.module.bpm.service.oa.BpmOALeaveService;
import cn.iocoder.yudao.module.bpm.service.oa.BpmOALeaveServiceImpl;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.ProjectApprovalUpdateReqVO;
import cn.iocoder.yudao.module.jl.service.project.ProjectApprovalServiceImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * OA 请假单的结果的监听器实现类
 *
 * @author 芋道源码
 */
@Component
public class BpmProjectChangeStatusResultListener extends BpmProcessInstanceResultEventListener {

    @Resource
    private ProjectApprovalServiceImpl projectApprovalService;

    @Override
    protected String getProcessDefinitionKey() {
        return ProjectApprovalServiceImpl.PROCESS_KEY;
    }

    @Override
    protected void onEvent(BpmProcessInstanceResultEvent event) {
        long id = Long.parseLong(event.getBusinessKey());
        String result = event.getResult().toString();
        //获取
        ProjectApprovalUpdateReqVO projectApprovalUpdateReqVO = new ProjectApprovalUpdateReqVO();
        projectApprovalUpdateReqVO.setId(id);
        projectApprovalUpdateReqVO.setApprovalStage(result);
        projectApprovalService.updateProjectApproval(projectApprovalUpdateReqVO);
    }

}
