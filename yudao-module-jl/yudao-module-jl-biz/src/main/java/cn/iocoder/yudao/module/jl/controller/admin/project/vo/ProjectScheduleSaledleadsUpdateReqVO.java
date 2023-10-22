package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo.ProjectCategoryAttachmentBaseVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Schema(description = "管理后台 - 项目报价保存 Request VO")
@Data
@ToString(callSuper = true)
public class ProjectScheduleSaledleadsUpdateReqVO {
    @Schema(description = "项目id", example = "1", nullable = true, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long projectId;
    @Schema(description = "安排单id", example = "1", nullable = true, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long scheduleId;
}
