package cn.iocoder.yudao.module.jl.controller.admin.crm.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema(description = "管理后台 - 销售线索更新 Request VO")
@Data
@ToString(callSuper = true)
public class SalesleadSeasVO {

    @Schema(description = "商机id", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "1")
    private Long id;

    @Schema(description = "备注" )
    private String jsonLog;

}
