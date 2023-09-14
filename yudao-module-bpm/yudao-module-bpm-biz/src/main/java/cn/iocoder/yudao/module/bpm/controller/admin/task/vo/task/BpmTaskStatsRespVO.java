package cn.iocoder.yudao.module.bpm.controller.admin.task.vo.task;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Schema(description = "管理后台 - 流程任务的 Running 进行中的分页项 Response VO")
@Data
public class BpmTaskStatsRespVO {

    @Schema(description = "待办数量", example = "1024")
    private Long todoCount;



}
