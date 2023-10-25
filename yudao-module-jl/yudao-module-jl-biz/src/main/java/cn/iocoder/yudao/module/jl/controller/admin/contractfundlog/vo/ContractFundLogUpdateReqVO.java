package cn.iocoder.yudao.module.jl.controller.admin.contractfundlog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 合同收款记录更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContractFundLogUpdateReqVO extends ContractFundLogBaseVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED, example = "21382")
    @NotNull(message = "ID不能为空")
    private Long id;

}
