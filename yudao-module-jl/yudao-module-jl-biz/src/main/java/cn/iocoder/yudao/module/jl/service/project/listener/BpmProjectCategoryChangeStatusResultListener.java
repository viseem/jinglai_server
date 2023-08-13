package cn.iocoder.yudao.module.jl.service.project.listener;

import cn.iocoder.yudao.module.bpm.framework.bpm.core.event.BpmProcessInstanceResultEvent;
import cn.iocoder.yudao.module.bpm.framework.bpm.core.event.BpmProcessInstanceResultEventListener;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.ProjectApprovalUpdateReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo.ProjectCategoryApprovalUpdateReqVO;
import cn.iocoder.yudao.module.jl.service.project.ProjectApprovalServiceImpl;
import cn.iocoder.yudao.module.jl.service.projectcategory.ProjectCategoryApprovalServiceImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * OA 请假单的结果的监听器实现类
 *
 * @author 芋道源码
 */
@Component
public class BpmProjectCategoryChangeStatusResultListener extends BpmProcessInstanceResultEventListener {

    @Resource
    private ProjectCategoryApprovalServiceImpl projectCategoryApprovalService;

    @Override
    protected String getProcessDefinitionKey() {
        return ProjectCategoryApprovalServiceImpl.PROCESS_KEY;
    }

    @Override
    protected void onEvent(BpmProcessInstanceResultEvent event) {
        long id = Long.parseLong(event.getBusinessKey());
        String result = event.getResult().toString();
        //获取
        ProjectCategoryApprovalUpdateReqVO projectCategoryApprovalUpdateReqVO = new ProjectCategoryApprovalUpdateReqVO();
        projectCategoryApprovalUpdateReqVO.setId(id);
        projectCategoryApprovalUpdateReqVO.setApprovalStage(result);
        projectCategoryApprovalService.updateProjectCategoryApproval(projectCategoryApprovalUpdateReqVO);
    }

}
