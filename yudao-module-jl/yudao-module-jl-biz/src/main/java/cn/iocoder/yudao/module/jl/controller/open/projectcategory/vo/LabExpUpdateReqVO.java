package cn.iocoder.yudao.module.jl.controller.open.projectcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 公司资产（设备）分页 Request VO")
@Data
@ToString(callSuper = true)
public class LabExpUpdateReqVO {

    @Schema(description = "岗位ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "4960")
    @NotNull(message = "岗位ID不能为空")
    private Long id;



    @Schema(description = "状态")
    private String stage;




}
