package cn.iocoder.yudao.module.jl.controller.admin.asset.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "管理后台 - 公司资产（设备）更新 Request VO")
@Data
@ToString(callSuper = true)
public class AssetDeviceLinkCateReqVO  {
    @Schema(description = "设备id", requiredMode = Schema.RequiredMode.REQUIRED, example = "19480")
    @NotNull(message = "设备id不能为空")
    List<Long> deviceIds;

    @Schema(description = "分类id", requiredMode = Schema.RequiredMode.REQUIRED, example = "19480")
    @NotNull(message = "分类id不能为空")
    Long cateId;

}
