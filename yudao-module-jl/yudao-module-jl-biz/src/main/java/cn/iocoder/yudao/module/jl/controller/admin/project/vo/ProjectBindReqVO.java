package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Schema(description = "管理后台 - 项目管理创建 Request VO")
@Data
@ToString(callSuper = true)
public class ProjectBindReqVO  {

    private Long contractId;
    private Long projectId;
}
