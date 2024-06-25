package cn.iocoder.yudao.module.jl.controller.admin.taskarrangerelation.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 任务安排关系 Order 设置，用于分页使用
 */
@Data
public class TaskArrangeRelationPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String taskId;

    @Schema(allowableValues = {"desc", "asc"})
    private String chargeItemId;

    @Schema(allowableValues = {"desc", "asc"})
    private String productId;

}
