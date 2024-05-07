package cn.iocoder.yudao.module.jl.controller.admin.crm.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Schema(description = "管理后台 - 用户导入 Response VO")
@Data
@Builder
public class InstitutionImportRespVO {

    @Schema(description = "导入成功的机构名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> createNames;

    @Schema(description = "更新成功的用户名数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> updateNames;

    @Schema(description = "导入失败的机构集合,key 为用户名，value 为失败原因", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> failureNames;

}