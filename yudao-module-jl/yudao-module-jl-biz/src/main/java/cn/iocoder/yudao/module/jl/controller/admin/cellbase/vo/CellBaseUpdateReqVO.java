package cn.iocoder.yudao.module.jl.controller.admin.cellbase.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 细胞数据更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CellBaseUpdateReqVO extends CellBaseBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "4935")
    @NotNull(message = "ID不能为空")
    private Long id;

}
