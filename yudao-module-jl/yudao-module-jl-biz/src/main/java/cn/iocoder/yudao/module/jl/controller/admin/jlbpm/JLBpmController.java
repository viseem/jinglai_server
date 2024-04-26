package cn.iocoder.yudao.module.jl.controller.admin.jlbpm;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.bpm.controller.admin.task.vo.instance.BpmProcessInstanceCancelReqVO;
import cn.iocoder.yudao.module.bpm.controller.admin.task.vo.task.BpmTaskRejectReqVO;
import cn.iocoder.yudao.module.bpm.controller.admin.task.vo.task.BpmTaskReturnReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.jlbpm.service.JLBpmServiceImpl;
import cn.iocoder.yudao.module.jl.controller.admin.jlbpm.vo.JLBpmTaskReqVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;
import static cn.iocoder.yudao.framework.web.core.util.WebFrameworkUtils.getLoginUserId;

@Tag(name = "管理后台 - 公司资产（设备）")
@RestController
@RequestMapping("/jl-bpm/task")
@Validated
public class JLBpmController {


    @Resource
    private JLBpmServiceImpl jLBpmService;

    @PostMapping("/approve")
    @PermitAll
    @Operation(summary = "(分页)获得公司资产（设备）列表")
    public CommonResult<Boolean> getAssetDevicePage(@Valid @RequestBody JLBpmTaskReqVO reqVO) {
        jLBpmService.approveTask(reqVO);
        return success(true);
    }

    @PutMapping("/reject")
    @Operation(summary = "不通过任务")
    @PreAuthorize("@ss.hasPermission('bpm:task:update')")
    public CommonResult<Boolean> rejectTask(@Valid @RequestBody BpmTaskRejectReqVO reqVO) {
        jLBpmService.rejectTask(reqVO);
        return success(true);
    }

    @PutMapping("/cancel-instance")
    @Operation(summary = "取消流程")
    @PreAuthorize("@ss.hasPermission('bpm:task:update')")
    public CommonResult<Boolean> cancelInstance(@Valid @RequestBody BpmProcessInstanceCancelReqVO reqVO) {
        jLBpmService.cancelInstance(reqVO);
        return success(true);
    }

    @PutMapping("/return")
    @Operation(summary = "回退任务", description = "用于【流程详情】的【回退】按钮")
    @PreAuthorize("@ss.hasPermission('bpm:task:update')")
    public CommonResult<Boolean> returnTask(@Valid @RequestBody BpmTaskReturnReqVO reqVO) {
        jLBpmService.returnTask(reqVO);
        return success(true);
    }

}
