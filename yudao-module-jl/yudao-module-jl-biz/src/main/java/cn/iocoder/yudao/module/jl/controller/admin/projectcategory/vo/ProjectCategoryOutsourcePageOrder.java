package cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 项目实验委外 Order 设置，用于分页使用
 */
@Data
public class ProjectCategoryOutsourcePageOrder {

    @Schema(allowableValues = {"desc", "asc"})
    private String id;

    @Schema(allowableValues = {"desc", "asc"})
    private String createTime;

    @Schema(allowableValues = {"desc", "asc"})
    private String projectId;

    @Schema(allowableValues = {"desc", "asc"})
    private String projectCategoryId;

    @Schema(allowableValues = {"desc", "asc"})
    private String type;

    @Schema(allowableValues = {"desc", "asc"})
    private String content;

    @Schema(allowableValues = {"desc", "asc"})
    private String categorySupplierId;

    @Schema(allowableValues = {"desc", "asc"})
    private String supplierPrice;

    @Schema(allowableValues = {"desc", "asc"})
    private String salePrice;

    @Schema(allowableValues = {"desc", "asc"})
    private String buyPrice;

    @Schema(allowableValues = {"desc", "asc"})
    private String proofName;

    @Schema(allowableValues = {"desc", "asc"})
    private String proofUrl;

    @Schema(allowableValues = {"desc", "asc"})
    private String mark;

}
