package cn.iocoder.yudao.module.jl.controller.admin.animal.vo;

import cn.iocoder.yudao.module.jl.entity.animal.AnimalFeedLog;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 动物笼位更新 Request VO")
@Data
@ToString(callSuper = true)
public class AnimalBoxSaveReqVO{

    @Schema(description = "``", requiredMode = Schema.RequiredMode.REQUIRED, example = "16609")
    @NotNull(message = "``不能为空")
    private Long id;

    @Schema(description = "变更信息", requiredMode = Schema.RequiredMode.REQUIRED, example = "16609")
    @NotNull(message = "变更信息不能为空")
    private AnimalFeedLog log;

}
