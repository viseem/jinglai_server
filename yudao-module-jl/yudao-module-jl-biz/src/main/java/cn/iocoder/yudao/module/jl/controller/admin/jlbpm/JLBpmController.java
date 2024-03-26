package cn.iocoder.yudao.module.jl.controller.admin.jlbpm;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.module.jl.controller.admin.jlbpm.service.JLBpmServiceImpl;
import cn.iocoder.yudao.module.jl.controller.admin.jlbpm.vo.JLBpmTaskReqVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

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


}
