package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "管理后台 - 取货单申请更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SupplyPickupSaveReqVO extends SupplyPickupBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "25390")
    private Long id;

    private List<SupplyPickupItemCreateReqVO> items;
}
