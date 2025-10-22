package cn.iocoder.yudao.module.bpm.framework.flowable.core.behavior;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.iocoder.yudao.framework.common.enums.CommonStatusEnum;
import cn.iocoder.yudao.module.bpm.service.definition.BpmTaskAssignRuleService;
import cn.iocoder.yudao.module.system.api.user.AdminUserApi;
import cn.iocoder.yudao.module.system.api.user.dto.AdminUserRespDTO;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.flowable.bpmn.model.UserTask;
import org.flowable.common.engine.impl.el.ExpressionManager;
import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.impl.bpmn.behavior.UserTaskActivityBehavior;
import org.flowable.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.flowable.engine.impl.util.TaskHelper;
import org.flowable.task.service.TaskService;
import org.flowable.task.service.impl.persistence.entity.TaskEntity;

import java.util.List;
import java.util.Set;

/**
 * 自定义的【单个】流程任务的 assignee 负责人的分配
 * 第一步，基于分配规则，计算出分配任务的【单个】候选人。如果找不到，则直接报业务异常，不继续执行后续的流程；
 * 第二步，随机选择一个候选人，则选择作为 assignee 负责人。
 *
 * @author 芋道源码
 */
@Slf4j
public class BpmUserTaskActivityBehavior extends UserTaskActivityBehavior {

    @Setter
    private BpmTaskAssignRuleService bpmTaskRuleService;

    public BpmUserTaskActivityBehavior(UserTask userTask) {
        super(userTask);
    }

    @Override
    protected void handleAssignments(TaskService taskService, String assignee, String owner,
        List<String> candidateUsers, List<String> candidateGroups, TaskEntity task, ExpressionManager expressionManager,
        DelegateExecution execution, ProcessEngineConfigurationImpl processEngineConfiguration) {
        // 第一步，获得任务的候选用户
        Long assigneeUserId = null;
        try {
            assigneeUserId = calculateTaskCandidateUsers(execution);
        } catch (Exception e) {
            // 找不到候选人，自动跳过该节点
            log.warn("[handleAssignments][任务{}找不到候选人，系统自动跳过。异常：{}]", 
                    task.getName(), e.getMessage());
            // 设置一个临时的审批人（0表示系统），让任务能够正常创建
            TaskHelper.changeTaskAssignee(task, "0");
            // 使用 execution 设置流程变量，确保监听器能获取到
            execution.setVariable("_approverNotFound_" + task.getTaskDefinitionKey(), true);
            execution.setVariable("_autoSkipReason_" + task.getTaskDefinitionKey(), "找不到任务的审批人");
            return;
        }
        
        // 第二步，检查候选人是否为空
        if (assigneeUserId == null) {
            log.warn("[handleAssignments][任务{}的候选人为null，系统自动跳过]", task.getName());
            // 设置一个临时的审批人（0表示系统），让任务能够正常创建
            TaskHelper.changeTaskAssignee(task, "0");
            execution.setVariable("_approverNotFound_" + task.getTaskDefinitionKey(), true);
            execution.setVariable("_autoSkipReason_" + task.getTaskDefinitionKey(), "找不到任务的审批人");
            return;
        }
        
        // 第三步，检查审批人是否有效（在职且存在）
        if (!isApproverValid(assigneeUserId)) {
            // 审批人离职或不存在，设置标记，由后续的监听器自动完成任务
            log.warn("[handleAssignments][审批人{}离职或不存在，标记为自动通过]", assigneeUserId);
            // 设置为处理人（仅用于记录）
            TaskHelper.changeTaskAssignee(task, String.valueOf(assigneeUserId));
            // 使用 execution 设置流程变量
            execution.setVariable("_approverInvalid_" + task.getTaskDefinitionKey(), true);
            execution.setVariable("_autoApproveReason_" + task.getTaskDefinitionKey(), "审批人已离职或不存在");
            return;
        }
        
        // 第四步，审批人有效，设置作为负责人
        TaskHelper.changeTaskAssignee(task, String.valueOf(assigneeUserId));
    }
    
    /**
     * 验证审批人是否有效（在职且存在）
     * 
     * @param userId 审批人ID
     * @return true-有效，false-无效（离职或不存在）
     */
    private boolean isApproverValid(Long userId) {
        if (userId == null) {
            return false;
        }
        
        try {
            AdminUserApi adminUserApi = SpringUtil.getBean(AdminUserApi.class);
            AdminUserRespDTO user = adminUserApi.getUser(userId);
            if (user == null) {
                log.warn("[isApproverValid][用户不存在，userId={}]", userId);
                return false;
            }
            
            // 检查用户状态，DISABLE(1) 表示离职或禁用
            boolean isValid = CommonStatusEnum.ENABLE.getStatus().equals(user.getStatus());
            if (!isValid) {
                log.info("[isApproverValid][用户已离职或禁用，userId={}, status={}]", 
                        userId, user.getStatus());
            }
            return isValid;
        } catch (Exception e) {
            // 查询异常时认为用户无效，自动通过
            log.error("[isApproverValid][查询用户异常，userId={}]", userId, e);
            return false;
        }
    }

    private Long calculateTaskCandidateUsers(DelegateExecution execution) {
        // 情况一，如果是多实例的任务，例如说会签、或签等情况，则从 Variable 中获取。它的任务处理人在 BpmParallelMultiInstanceBehavior 中已经被分配了
        if (super.multiInstanceActivityBehavior != null) {
            return execution.getVariable(super.multiInstanceActivityBehavior.getCollectionElementVariable(), Long.class);
        }

        // 情况二，如果非多实例的任务，则计算任务处理人
        // 第一步，先计算可处理该任务的处理人们
        Set<Long> candidateUserIds = bpmTaskRuleService.calculateTaskCandidateUsers(execution);
        // 第二步，后随机选择一个任务的处理人
        // 疑问：为什么一定要选择一个任务处理人？
        // 解答：项目对 bpm 的任务是责任到人，所以每个任务有且仅有一个处理人。
        //      如果希望一个任务可以同时被多个人处理，可以考虑使用 BpmParallelMultiInstanceBehavior 实现的会签 or 或签。
        int index = RandomUtil.randomInt(candidateUserIds.size());
        return CollUtil.get(candidateUserIds, index);
    }

}
