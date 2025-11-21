package cn.iocoder.yudao.module.jl.jlBpmListener;

import cn.iocoder.yudao.module.bpm.enums.message.BpmMessageEnum;
import cn.iocoder.yudao.module.bpm.enums.task.BpmProcessInstanceResultEnum;
import cn.iocoder.yudao.module.bpm.framework.bpm.core.event.BpmProcessInstanceResultEvent;
import cn.iocoder.yudao.module.bpm.framework.bpm.core.event.BpmProcessInstanceResultEventListener;
import cn.iocoder.yudao.module.jl.entity.project.ProjectOnly;
import cn.iocoder.yudao.module.jl.enums.ProjectStageEnums;
import cn.iocoder.yudao.module.jl.repository.project.ProjectOnlyRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectRepository;
import cn.iocoder.yudao.module.jl.service.project.ProjectServiceImpl;
import cn.iocoder.yudao.module.system.api.notify.NotifyMessageSendApi;
import cn.iocoder.yudao.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * OA 请假单的结果的监听器实现类
 *
 * @author 芋道源码
 */
@Component
public class BpmProjectOutboundApplyResultListener extends BpmProcessInstanceResultEventListener {

    @Resource
    private ProjectRepository projectRepository;

    @Resource
    private ProjectOnlyRepository projectOnlyRepository;

    @Resource
    private AdminUserApi adminUserApi;

    @Resource
    private NotifyMessageSendApi notifyMessageSendApi;

    @Override
    protected String getProcessDefinitionKey() {
        return ProjectServiceImpl.PROCESS_KEY;
    }

    @Override
    @Transactional
    public void onEvent(BpmProcessInstanceResultEvent event) {
        long id = Long.parseLong(event.getBusinessKey());
        String result = event.getResult().toString();

        //如果是审核通过
        if (String.valueOf(BpmProcessInstanceResultEnum.APPROVE.getResult()).equals(result)){
            projectRepository.updateStageById(ProjectStageEnums.OUTED.getStatus(), id);

            // 发送消息给角色为 "projectM" 的用户
            System.out.println("1-----");
            sendNotificationToProjectRelations(id);
        }
        projectRepository.updateOutboundApplyResultById(result, id);
        //获取
    }

    /**
     * 发送出库审批通过的消息给项目经理角色的用户
     *
     * @param projectId 项目ID
     */
    private void sendNotificationToProjectRelations(Long projectId) {
        try {
            // 1. 获取项目信息
            Optional<ProjectOnly> projectOptional = projectOnlyRepository.findById(projectId);
            if (!projectOptional.isPresent()) {
                return;
            }
            ProjectOnly project = projectOptional.get();

            // 2. 获取角色为 "projectM" 的所有用户
            List<AdminUserRespDTO> projectRelations = adminUserApi.getUserListByRoleCode("animalFeedManager");
            System.out.println("projectRelations---"+ JSON.toJSONString(projectRelations));
            if (projectRelations == null || projectRelations.isEmpty()) {
                return;
            }

            // 3. 准备消息模板参数
            Map<String, Object> templateParams = new HashMap<>();
            templateParams.put("projectId", project.getId());
            templateParams.put("projectName", project.getName());
            String content = String.format("项目(编号:%s，名称:%s)的出库申请已审批通过，请及时查看",
                    project.getId(), project.getName());
            templateParams.put("content", content);

            // 4. 发送消息给每个项目经理
            for (AdminUserRespDTO user : projectRelations) {
                if (user.getId() == null) {
                    continue;
                }
                notifyMessageSendApi.sendSingleMessageToAdmin(new NotifySendSingleToUserReqDTO(
                        user.getId(),
                        BpmMessageEnum.NOTIFY_WHEN_PROJECT_STAGE_CHANGE.getTemplateCode(),
                        templateParams
                ));
            }
        } catch (Exception e) {
            // 记录日志但不影响主流程
            System.err.println("发送出库审批通过消息给项目经理失败: " + e.getMessage());
        }
    }

}
