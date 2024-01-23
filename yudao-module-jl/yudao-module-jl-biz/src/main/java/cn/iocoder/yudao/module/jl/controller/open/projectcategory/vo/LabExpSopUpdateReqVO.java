package cn.iocoder.yudao.module.jl.controller.open.projectcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 公司资产（设备）分页 Request VO")
@Data
@ToString(callSuper = true)
public class LabExpSopUpdateReqVO {

    @Schema(description = "岗位ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "4960")
    @NotNull(message = "岗位ID不能为空")
    private Long id;

    @Schema(description = "原始实验名目 id")
    private Long categoryId;

    @Schema(description = "操作步骤的内容")
    private String content;

    @Schema(description = "步骤序号")
    private Integer step;

    @Schema(description = "注意事项")
    private String mark;

    @Schema(description = "状态")
    private String status;

    @Schema(description = "依赖项(json数组多个)")
    private String dependIds;


}
