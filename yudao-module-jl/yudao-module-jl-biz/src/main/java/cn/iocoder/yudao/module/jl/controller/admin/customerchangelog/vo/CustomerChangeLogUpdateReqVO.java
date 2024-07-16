package cn.iocoder.yudao.module.jl.controller.admin.customerchangelog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 客户变更日志更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerChangeLogUpdateReqVO extends CustomerChangeLogBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "11362")
    @NotNull(message = "ID不能为空")
    private Long id;

}
