package cn.iocoder.yudao.module.jl.controller.admin.commontask.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

@Schema(description = "管理后台 - 通用任务下发 Request VO")
@Data
@ToString(callSuper = true)
public class CommonTaskSendReqVO {
    @Schema(description = "报价id", example = "25871")
    private Long quotationId;
}
