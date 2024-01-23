package cn.iocoder.yudao.module.jl.controller.admin.taskrelation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 任务关系依赖 Order 设置，用于分页使用
 */
@Data
public class TaskRelationPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String level;

    @Schema(allowableValues = {"desc", "asc"})
    private String taskId;

    @Schema(allowableValues = {"desc", "asc"})
    private String dependId;

}
