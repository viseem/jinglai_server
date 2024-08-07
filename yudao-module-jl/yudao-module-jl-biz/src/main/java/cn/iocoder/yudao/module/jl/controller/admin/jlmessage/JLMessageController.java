package cn.iocoder.yudao.module.jl.controller.admin.jlmessage;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.bpm.controller.admin.task.vo.instance.BpmProcessInstanceCancelReqVO;
import cn.iocoder.yudao.module.bpm.controller.admin.task.vo.task.BpmTaskRejectReqVO;
import cn.iocoder.yudao.module.bpm.controller.admin.task.vo.task.BpmTaskReturnReqVO;
import cn.iocoder.yudao.module.bpm.enums.message.BpmMessageEnum;
import cn.iocoder.yudao.module.jl.controller.admin.jlbpm.service.JLBpmServiceImpl;
import cn.iocoder.yudao.module.jl.controller.admin.jlbpm.vo.JLBpmTaskReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.jlmessage.vo.JLMessageReqVO;
import cn.iocoder.yudao.module.jl.entity.user.User;
import cn.iocoder.yudao.module.jl.repository.user.UserRepository;
import cn.iocoder.yudao.module.system.api.notify.NotifyMessageSendApi;
import cn.iocoder.yudao.module.system.api.notify.dto.NotifySendSingleToUserReqDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static cn.iocoder.yudao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.security.core.util.SecurityFrameworkUtils.getLoginUserId;
import static cn.iocoder.yudao.module.jl.enums.ErrorCodeConstants.USER_NOT_EXISTS;

@Tag(name = "管理后台 - 消息通知")
@RestController
@RequestMapping("/jl-msg")
@Validated
public class JLMessageController {
    @Resource
    private NotifyMessageSendApi notifyMessageSendApi;

    @Resource
    private UserRepository userRepository;

    @PostMapping("/send-urge")
    @Operation(summary = "发送催办消息")
    public CommonResult<Boolean> sendUrgeMsg(@Valid @RequestBody JLMessageReqVO reqVO) {

        Optional<User> byId = userRepository.findById(getLoginUserId());
        if (byId.isEmpty()) {
            throw exception(USER_NOT_EXISTS);
        }
        User user = byId.get();

        String content = String.format("您收到(%s)的催办消息：%s。",
                user.getNickname(),
                reqVO.getTitle());
        Map<String, Object> templateParams = new HashMap<>();
        templateParams.put("msgType", reqVO.getMsgType());
        templateParams.put("refId", reqVO.getRefId());
        templateParams.put("projectName", reqVO.getProjectName());
        templateParams.put("content", content + (reqVO.getMark() != null ? "备注：" + reqVO.getMark() : ""));
        templateParams.put("processInstanceId", reqVO.getProcessInstanceId());
        notifyMessageSendApi.sendSingleMessageToAdmin(new NotifySendSingleToUserReqDTO(
                reqVO.getSendToUserId(),
                BpmMessageEnum.URGE_DO.getTemplateCode(), templateParams
        ));
        return success(true);
    }

}
