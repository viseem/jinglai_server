package cn.iocoder.yudao.module.jl.controller.admin.contractfundlog.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Schema(description = "管理后台 - 用户导入 Response VO")
@Data
@Builder
public class ContractFundLogImportRespVO {

    @Schema(description = "创建成功的转账单位", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> createNames;

    @Schema(description = "更新成功的转账单位", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> updateNames;

    @Schema(description = "导入失败的用户集合,key 为名称，value 为失败原因", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, String> failureNames;

}