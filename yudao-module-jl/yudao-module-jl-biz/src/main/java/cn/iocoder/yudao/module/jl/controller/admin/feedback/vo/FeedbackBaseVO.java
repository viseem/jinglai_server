package cn.iocoder.yudao.module.jl.controller.admin.feedback.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 晶莱反馈 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class FeedbackBaseVO {

    @Schema(description = "内容", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "内容不能为空")
    private String content;

    @Schema(description = "重要程度：重要紧急 不重要不紧急 重要不紧急", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "重要程度：重要紧急 不重要不紧急 重要不紧急不能为空")
    private String importance;

    @Schema(description = "期望截止日期（排期）")
    private String deadline;

    @Schema(description = "状态：未受理、处理中、已解决", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "状态：未受理、处理中、已解决不能为空")
    private String status = "1";

    @Schema(description = "截图地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @NotNull(message = "截图地址不能为空")
    private String fileUrl;

}
