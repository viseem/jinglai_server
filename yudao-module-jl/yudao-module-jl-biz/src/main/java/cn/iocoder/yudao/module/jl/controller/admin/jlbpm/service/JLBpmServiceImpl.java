package cn.iocoder.yudao.module.jl.controller.admin.jlbpm.service;

import cn.iocoder.yudao.module.bpm.controller.admin.task.vo.task.BpmTaskApproveReqVO;
import cn.iocoder.yudao.module.bpm.service.task.BpmTaskServiceImpl;
import cn.iocoder.yudao.module.jl.controller.admin.jlbpm.vo.JLBpmTaskReqVO;
import cn.iocoder.yudao.module.jl.repository.project.ProcurementRepository;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import java.util.Objects;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.web.core.util.WebFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.bpm.service.utils.ProcessInstanceKeyConstants.PROJECT_PROCUREMENT_AUDIT;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.BPM_PARAMS_ERROR;

/**
 * 项目的实验名目 Service 实现类
 *
 */
@Service
@Validated
public class JLBpmServiceImpl implements JLBpmService {

    @Resource
    private BpmTaskServiceImpl taskService;

    @Resource
    private ProcurementRepository procurementRepository;

    @Override
    @Transactional
    public void approveTask(JLBpmTaskReqVO approveReqVO) {

        ProcessInstance instance = taskService.getProcessInstanceByTaskId(approveReqVO.getId());

        String processDefinitionKey = instance.getProcessDefinitionKey();

        if(Objects.equals(processDefinitionKey,PROJECT_PROCUREMENT_AUDIT)){
            if(approveReqVO.getRefId()==null){
                throw exception(BPM_PARAMS_ERROR);
            }
        procurementRepository.updateStatusById(approveReqVO.getRefId(), String.valueOf(approveReqVO.getTaskIndex()+1));
        }

        BpmTaskApproveReqVO bpmTaskApproveReqVO = new BpmTaskApproveReqVO();
        bpmTaskApproveReqVO.setId(approveReqVO.getId());
        bpmTaskApproveReqVO.setReason(approveReqVO.getReason());
        taskService.approveTask(getLoginUserId(), bpmTaskApproveReqVO);
    }

}