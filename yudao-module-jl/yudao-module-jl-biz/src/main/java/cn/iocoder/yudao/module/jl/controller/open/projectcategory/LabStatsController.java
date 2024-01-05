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

@Tag(name = "管理后台 - 实验室统计")
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
        Integer doingCount = projectCategoryRepository.countByStageAndAndLabIdAndType(ProjectCategoryStatusEnums.DOING.getStatus(), statsVO.getLabId());
        Integer notDoCount = projectCategoryRepository.countByStageAndAndLabIdAndType(ProjectCategoryStatusEnums.WAIT_DO.getStatus(), statsVO.getLabId());
        Integer dataCheckCount = projectCategoryRepository.countByStageAndAndLabIdAndType(ProjectCategoryStatusEnums.DATA_CHECK.getStatus(), statsVO.getLabId());
        Integer dataAcceptCount = projectCategoryRepository.countByStageAndAndLabIdAndType(ProjectCategoryStatusEnums.DATA_ACCEPT.getStatus(), statsVO.getLabId());
        Integer dataRejectCount = projectCategoryRepository.countByStageAndAndLabIdAndType(ProjectCategoryStatusEnums.DATA_REJECT.getStatus(), statsVO.getLabId());
        Integer pauseCount = projectCategoryRepository.countByStageAndAndLabIdAndType(ProjectCategoryStatusEnums.PAUSE.getStatus(), statsVO.getLabId());

        LabStatsExpCountRespVO respVO = new LabStatsExpCountRespVO();
        respVO.setDoingCount(doingCount);
        respVO.setNotDoCount(notDoCount);
        respVO.setDataCheckCount(dataCheckCount);
        respVO.setDataAcceptCount(dataAcceptCount);
        respVO.setDataRejectCount(dataRejectCount);
        respVO.setPauseCount(pauseCount);
        return success(respVO);
    }

}
