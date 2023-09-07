package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 项目实验方案 Order 设置，用于分页使用
 */
@Data
public class ProjectPlanPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String projectId;

    @Schema(allowableValues = {"desc", "asc"})
    private String customerId;

    @Schema(allowableValues = {"desc", "asc"})
    private String scheduleId;

    @Schema(allowableValues = {"desc", "asc"})
    private String planText;

    @Schema(allowableValues = {"desc", "asc"})
    private String version;

}
