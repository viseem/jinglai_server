package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 项目采购单物流信息创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ProcurementShipmentCreateReqVO extends ProcurementShipmentBaseVO {

    @Schema(description = "采购单id", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "31877")
    private Long procurementId;
}