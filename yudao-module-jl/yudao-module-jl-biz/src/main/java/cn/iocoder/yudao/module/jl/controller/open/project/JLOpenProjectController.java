package cn.iocoder.yudao.module.jl.controller.open.project;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.framework.web.core.util.WebFrameworkUtils;
import cn.iocoder.yudao.module.bpm.enums.message.BpmMessageEnum;
import cn.iocoder.yudao.module.bpm.enums.task.BpmProcessInstanceResultEnum;
import cn.iocoder.yudao.module.jl.controller.admin.project.vo.*;
import cn.iocoder.yudao.module.jl.controller.admin.projectquotation.vo.ProjectQuotationRespVO;
import cn.iocoder.yudao.module.jl.entity.project.Project;
import cn.iocoder.yudao.module.jl.entity.project.ProjectCategoryOnly;
import cn.iocoder.yudao.module.jl.entity.project.ProjectSimple;
import cn.iocoder.yudao.module.jl.entity.projectquotation.ProjectQuotation;
import cn.iocoder.yudao.module.jl.enums.ProjectStageEnums;
import cn.iocoder.yudao.module.jl.mapper.project.ProjectMapper;
import cn.iocoder.yudao.module.jl.mapper.projectquotation.ProjectQuotationMapper;
import cn.iocoder.yudao.module.jl.repository.project.ProjectOnlyRepository;
import cn.iocoder.yudao.module.jl.repository.project.ProjectRepository;
import cn.iocoder.yudao.module.jl.service.commontask.CommonTaskServiceImpl;
import cn.iocoder.yudao.module.jl.service.project.ProjectCategoryService;
import cn.iocoder.yudao.module.jl.service.project.ProjectService;
import cn.iocoder.yudao.module.jl.service.project.ProjectServiceImpl;
import cn.iocoder.yudao.module.jl.service.projectquotation.ProjectQuotationService;
import cn.iocoder.yudao.module.system.api.notify.NotifyMessageSendApi;
import cn.iocoder.yudao.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;
import cn.iocoder.yudao.module.system.controller.admin.util.vo.UtilStoreGetReqVO;
import cn.iocoder.yudao.module.system.service.util.UtilServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.PROJECT_QUOTATION_NOT_EXISTS;

@Tag(name = "管理后台 - 报价开放接口")
@RestController
@RequestMapping("/project")
@Validated
public class JLOpenProjectController {
    @Resource
    private ProjectService projectService;

    @Resource
    private ProjectServiceImpl projectServiceImpl;

    @Resource
    private ProjectMapper projectMapper;

    @Resource
    private ProjectOnlyRepository projectOnlyRepository;

    @Resource
    private CommonTaskServiceImpl commonTaskService;


    @Resource
    private UtilServiceImpl utilService;

    @Resource
    private NotifyMessageSendApi notifyMessageSendApi;


    @PostMapping("/get")
    @Operation(summary = "通过 ID 获得项目")
    @Parameter(name = "id", description = "编号", required = true, example = "1024")
    public CommonResult<ProjectRespVO> getProjectQuotation(@RequestParam("id") Long id, @RequestBody @Valid UtilStoreGetReqVO pageVO) {
        utilService.validStoreWithException(pageVO);
        Optional<Project> project = projectService.getProject(id);
        return success(project.map(projectMapper::toDto).orElseThrow(() -> exception(PROJECT_QUOTATION_NOT_EXISTS)));
    }


    @PostMapping("/customer-sign")
    @Operation(summary = "通过 ID 获得项目")
    @Transactional
    public CommonResult<Boolean> getProjectQuotation(@RequestBody @Valid ProjectCustomerSignReqVO vo) {
        utilService.validStoreWithException(vo);
        ProjectSimple projectSimple = projectServiceImpl.validateProjectExists(vo.getProjectId());
        //发送通知给项目相关负责人
        HashSet<Long> userIds = new HashSet<>();
        userIds.add(projectSimple.getManagerId());
        userIds.add(projectSimple.getSalesId());
        Map<String, Object> templateParams = new HashMap<>();
        String content = String.format(
                "客户(%s)的项目(%s)安排单已签字，请及时处理",
                projectSimple.getCustomer()!=null?projectSimple.getCustomer().getName():"",
                projectSimple.getName()
        );
        templateParams.put("projectName", projectSimple.getName());
        templateParams.put("content", content);
        templateParams.put("id", vo.getProjectId());
        for (Long userId : userIds) {
            notifyMessageSendApi.sendSingleMessageToAdmin(new NotifySendSingleToUserReqDTO(
                    userId,
                    BpmMessageEnum.NOTIFY_WHEN_TASK_ARRANGE_CUSTOMER_SIGN.getTemplateCode(), templateParams
            ));
        }


        // 修改项目的客户签字的相关信息
        projectOnlyRepository.updateCustomerSignImgUrlAndCustomerSignMarkAndCustomerSignTimeById(vo.getCustomerSignImgUrl(),vo.getCustomerSignMark(), LocalDateTime.now(),vo.getProjectId());
        // 如果项目的内部审批已经通过，则把未下发的任务下发一遍
        if(Objects.equals(projectSimple.getDoApplyResult(), BpmProcessInstanceResultEnum.APPROVE.getResult().toString())){
            //直接改一下项目状态
            projectOnlyRepository.updateStageById(ProjectStageEnums.DOING.getStatus(),vo.getProjectId());
            //改一下实验任务的状态，把 未下发的改为 开展中
            if(projectSimple.getCurrentQuotationId()!=null){
                commonTaskService.sendTaskAndMsg(projectSimple.getCurrentQuotationId(), projectSimple.getName(), projectSimple.getId());
            }
        }
        return success(true);
    }
}
