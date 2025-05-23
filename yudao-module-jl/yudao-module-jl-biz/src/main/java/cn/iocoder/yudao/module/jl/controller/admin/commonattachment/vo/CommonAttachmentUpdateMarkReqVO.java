package cn.iocoder.yudao.module.jl.controller.admin.commonattachment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 通用附件更新 Request VO")
@Data
@ToString(callSuper = true)
public class CommonAttachmentUpdateMarkReqVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24278")
    @NotNull(message = "ID不能为空")
    private Long id;


    @Schema(description = "备注")
    private String mark;
}
