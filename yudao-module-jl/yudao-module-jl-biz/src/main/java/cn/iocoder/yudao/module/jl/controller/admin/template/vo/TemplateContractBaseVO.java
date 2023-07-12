package cn.iocoder.yudao.module.jl.controller.admin.template.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 合同模板 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class TemplateContractBaseVO {

    @Schema(description = "文件名", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotNull(message = "文件名不能为空")
    private String fileName;

    @Schema(description = "文件地址", requiredMode = Schema.RequiredMode.REQUIRED, example = "https://www.iocoder.cn")
    @NotNull(message = "文件地址不能为空")
    private String fileUrl;

    @Schema(description = "关键备注")
    private String mark;

    @Schema(description = "合同类型：项目合同、饲养合同", example = "2")
    private String type;

    @Schema(description = "合同用途")
    private String useWay;

    @Schema(description = "合同名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "芋艿")
    @NotNull(message = "合同名称不能为空")
    private String name;

}
