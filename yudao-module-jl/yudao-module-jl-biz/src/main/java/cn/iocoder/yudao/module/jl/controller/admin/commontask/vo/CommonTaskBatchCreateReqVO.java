package cn.iocoder.yudao.module.jl.controller.admin.commontask.vo;

import cn.iocoder.yudao.module.jl.entity.project.ProjectChargeitem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "管理后台 - 通用任务批量创建 Request VO")
@Data
@ToString(callSuper = true)
public class CommonTaskBatchCreateReqVO {

    @Schema(description = "负责人", requiredMode = Schema.RequiredMode.REQUIRED, example = "[]")
    @NotNull(message = "负责人不能为空")
    private Long userId;

    @Schema(description = "负责人", requiredMode = Schema.RequiredMode.REQUIRED, example = "[]")
    @NotNull(message = "负责人昵称不能为空")
    private String userNickname;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "收费项", requiredMode = Schema.RequiredMode.REQUIRED, example = "[]")
    @NotNull(message = "收费项不能为空")
    private List<ProjectChargeitem> chargeItemList;
}
