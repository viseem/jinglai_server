package cn.iocoder.yudao.module.jl.controller.admin.crm.vo;

import cn.iocoder.yudao.module.jl.entity.project.ProjectConstract;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Schema(description = "管理后台 - 销售线索更新 Request VO")
@Data
@ToString(callSuper = true)
public class SalesleadUpdateManagerVO {

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "1")
    private Long id;

    @Schema(description = "绑定报价人员" ,requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "1")
    private Long managerId;

    @Schema(description = "备注" )
    private String assignMark;
}
