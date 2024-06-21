package cn.iocoder.yudao.module.jl.controller.admin.taskproduct.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 任务产品中间 Order 设置，用于分页使用
 */
@Data
public class TaskProductPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String taskId;

    @Schema(allowableValues = {"desc", "asc"})
    private String productId;

}
