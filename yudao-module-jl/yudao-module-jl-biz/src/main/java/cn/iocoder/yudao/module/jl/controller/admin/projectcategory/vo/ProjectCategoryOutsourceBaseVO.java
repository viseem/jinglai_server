package cn.iocoder.yudao.module.jl.controller.admin.projectcategory.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 项目实验委外 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProjectCategoryOutsourceBaseVO {

    @Schema(description = "项目id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1356")
    @NotNull(message = "项目id不能为空")
    private Long projectId;

    @Schema(description = "项目的实验名目id")
    private Long projectCategoryId;

    @Schema(description = "类型：项目、实验、其它", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "类型：项目、实验、其它不能为空")
    private String type;

    @Schema(description = "外包内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "外包内容不能为空")
    private String content;

    @Schema(description = "外包供应商id", example = "20117")
    private Integer categorySupplierId;

    @Schema(description = "供应商报价")
    private Integer supplierPrice;

    @Schema(description = "销售价格", requiredMode = Schema.RequiredMode.REQUIRED, example = "32231")
    @NotNull(message = "销售价格不能为空")
    private Integer salePrice;

    private Long scheduleId;

    @Schema(description = "购买价格", example = "12412")
    private Integer buyPrice;

    @Schema(description = "凭证名字", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotNull(message = "凭证名字不能为空")
    private String proofName;

    @Schema(description = "凭证地址 ", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @NotNull(message = "凭证地址 不能为空")
    private String proofUrl;

    @Schema(description = "备注")
    private String mark;

    @Schema(description = "原始数据")
    private String rawdata;

    @Schema(description = "实验记录")
    private String record;

    @Schema(description = "附件")
    private String files;

}
