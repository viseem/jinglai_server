package cn.iocoder.yudao.module.jl.controller.admin.inventory.vo;

import cn.iocoder.yudao.module.jl.entity.inventory.SupplyOutItem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 出库申请 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SupplyOutRespVO extends SupplyOutBaseVO {

    @Schema(description = "岗位ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "9504")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    private List<SupplyOutItemRespVO> items;

}
