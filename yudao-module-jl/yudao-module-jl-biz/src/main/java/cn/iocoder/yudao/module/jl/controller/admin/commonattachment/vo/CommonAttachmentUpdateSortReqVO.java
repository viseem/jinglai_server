package cn.iocoder.yudao.module.jl.controller.admin.commonattachment.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "管理后台 - 通用附件排序更新 Request VO")
@Data
@ToString(callSuper = true)
public class CommonAttachmentUpdateSortReqVO {

    @Schema(description = "附件排序列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "附件排序列表不能为空")
    @Valid
    private List<AttachmentSortItem> items;

    @Data
    public static class AttachmentSortItem {
        @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "24278")
        @NotNull(message = "ID不能为空")
        private Long id;

        @Schema(description = "排序值", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
        @NotNull(message = "排序值不能为空")
        private Integer sort;
    }
}
