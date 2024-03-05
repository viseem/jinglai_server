package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema(description = "管理后台 - 项目更新 Request VO")
@Data
@ToString(callSuper = true)
public class ProjectSeasVO {

    @Schema(description = "项目id", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "1")
    private Long id;

    @Schema(description = "备注" )
    private String transferLog;

    @Schema(description = "类型" )
    private Integer type;

}
