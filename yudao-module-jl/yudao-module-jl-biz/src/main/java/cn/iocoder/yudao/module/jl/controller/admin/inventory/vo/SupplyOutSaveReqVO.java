package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Schema(description = "管理后台 - 出库申请save Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SupplyOutSaveReqVO extends SupplyOutBaseVO {
    @Schema(description = "ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "10832")
    private Long id;
    List<SupplyOutItemSaveReqVO> items;

}
