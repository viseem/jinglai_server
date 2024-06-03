package cn.iocoder.yudao.module.jl.controller.admin.commontask.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 通用任务 Response VO")
@Data
@ToString(callSuper = true)
public class CommonTaskCountStatusRespVO  {

    @Schema(description = "未完成数量")
    private Integer undoneCount;

}
