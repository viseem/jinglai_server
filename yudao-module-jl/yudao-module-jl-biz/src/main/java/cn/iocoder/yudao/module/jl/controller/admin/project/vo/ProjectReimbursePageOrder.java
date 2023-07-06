package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 项目报销 Order 设置，用于分页使用
 */
@Data
public class ProjectReimbursePageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String type;

    @Schema(allowableValues = {"desc", "asc"})
    private String projectCategoryId;

    @Schema(allowableValues = {"desc", "asc"})
    private String projectId;

    @Schema(allowableValues = {"desc", "asc"})
    private String content;

    @Schema(allowableValues = {"desc", "asc"})
    private String proofName;

    @Schema(allowableValues = {"desc", "asc"})
    private String proofUrl;

    @Schema(allowableValues = {"desc", "asc"})
    private String price;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

}
