package cn.iocoder.yudao.module.jl.controller.admin.feedback.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 晶莱反馈 Order 设置，用于分页使用
 */
@Data
public class FeedbackPageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String content;

    @Schema(allowableValues = {"desc", "asc"})
    private String importance;

    @Schema(allowableValues = {"desc", "asc"})
    private String deadline;

    @Schema(allowableValues = {"desc", "asc"})
    private String status;

    @Schema(allowableValues = {"desc", "asc"})
    private String fileUrl;

}
