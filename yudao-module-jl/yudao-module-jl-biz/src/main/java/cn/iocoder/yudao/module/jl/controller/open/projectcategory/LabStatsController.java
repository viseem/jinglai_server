package cn.iocoder.yudao.module.jl.controller.open.projectcategory;

import cn.iocoder.yudao.framework.common.pojo.CommonResult;
import cn.iocoder.yudao.framework.common.pojo.PageResult;
import cn.iocoder.yudao.module.jl.controller.admin.asset.vo.AssetDevicePageOrder;
import cn.iocoder.yudao.module.jl.controller.admin.asset.vo.AssetDevicePageReqVO;
import cn.iocoder.yudao.module.jl.controller.admin.asset.vo.AssetDeviceRespVO;
import cn.iocoder.yudao.module.jl.controller.open.projectcategory.vo.LabExpStatsReqVO;
import cn.iocoder.yudao.module.jl.controller.open.projectcategory.vo.LabStatsExpCountRespVO;
import cn.iocoder.yudao.module.jl.entity.asset.AssetDevice;
import cn.iocoder.yudao.module.jl.enums.ProjectCategoryStatusEnums;
import cn.iocoder.yudao.module.jl.repository.project.ProjectCategoryRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.validation.Valid;

import static cn.iocoder.yudao.framework.common.pojo.CommonResult.success;

@Tag(name = "管理后台 - 公司资产（设备）")
@RestController
@RequestMapping("/lab/stats")
@Validated
public class LabStatsController {

    @Resource
    private ProjectCategoryRepository projectCategoryRepository;

    @GetMapping("/exp-count")
    @PermitAll
    @Operation(summary = "统计实验室的任务数量")
    public CommonResult<LabStatsExpCountRespVO> getExpCountStats(@Valid LabExpStatsReqVO statsVO) {
        Integer doingCount = projectCategoryRepository.countInStageAndAndLabIdAndType(ProjectCategoryStatusEnums.getDoingStages(), statsVO.getLabId());
        Integer notDoCount = projectCategoryRepository.countByStageAndAndLabIdAndType(ProjectCategoryStatusEnums.WAIT_DO.getStatus(), statsVO.getLabId());
        LabStatsExpCountRespVO respVO = new LabStatsExpCountRespVO();
        respVO.setDoingCount(doingCount);
        respVO.setNotDoCount(notDoCount);
        return success(respVO);
    }

}
