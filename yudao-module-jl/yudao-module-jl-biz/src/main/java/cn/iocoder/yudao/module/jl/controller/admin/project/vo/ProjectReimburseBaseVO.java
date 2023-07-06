package cn.iocoder.yudao.module.jl.controller.admin.project.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 项目报销 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ProjectReimburseBaseVO {

    @Schema(description = "报销类型：物流成本、差旅费、其它", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "报销类型：物流成本、差旅费、其它不能为空")
    private String type;

    @Schema(description = "项目的实验名目id", example = "29942")
    private Integer projectCategoryId;

    @Schema(description = "项目id", requiredMode = Schema.RequiredMode.REQUIRED, example = "15738")
    @NotNull(message = "项目id不能为空")
    private Integer projectId;

    @Schema(description = "报销内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "报销内容不能为空")
    private String content;

    @Schema(description = "凭证名字", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotNull(message = "凭证名字不能为空")
    private String proofName;

    private Long scheduleId;

    @Schema(description = "凭证地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @NotNull(message = "凭证地址不能为空")
    private String proofUrl;

    @Schema(description = "报销金额", requiredMode = Schema.RequiredMode.REQUIRED, example = "31733")
    @NotNull(message = "报销金额不能为空")
    private Integer price;

    @Schema(description = "备注")
    private String mark;

}
