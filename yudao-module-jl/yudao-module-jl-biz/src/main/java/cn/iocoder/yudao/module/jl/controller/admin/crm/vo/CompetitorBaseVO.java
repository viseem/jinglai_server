package cn.iocoder.yudao.module.jl.controller.admin.crm.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.validation.constraints.*;

/**
 * 友商 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class CompetitorBaseVO {

    @Schema(description = "公司名", requiredMode = Schema.RequiredMode.REQUIRED, example = "惟象科技")
    @NotNull(message = "公司名不能为空")
    private String name;

    @Schema(description = "联系人", example = "dev")
    private String contactName;

    @Schema(description = "手机号")
    private String phone;

    @Schema(description = "友商类型", example = "请选择")
    private String type;

    @Schema(description = "优势")
    private String advantage;

    @Schema(description = "劣势")
    private String disadvantage;

    @Schema(description = "备注")
    private String mark;

    /**
     * 官网
     */
    @Schema(description = "官网")
    private String website;

    /**
     * 公众号
     */
    @Schema(description = "公众号")
    private String wxWebsite;

}
